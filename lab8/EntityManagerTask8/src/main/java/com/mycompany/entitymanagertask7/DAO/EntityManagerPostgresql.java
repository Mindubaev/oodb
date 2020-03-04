/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask7.DAO;

import com.mycompany.entitymanagertask7.Annotations.Entity;
import com.mycompany.entitymanagertask7.Annotations.ManyToMany;
import com.mycompany.entitymanagertask7.Annotations.OneToOne;
import com.mycompany.entitymanagertask7.MetaClass.Attribute;
import com.mycompany.entitymanagertask7.MetaClass.EntityMeta;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Artur
 */
public class EntityManagerPostgresql implements EntityManager {

    private List<Object> managedObjects = new ArrayList<>();
    private List<EntityMeta> metaClasses;
    private Connection connection;
    private Properties props;

    public EntityManagerPostgresql(Properties properties, List<EntityMeta> metaClasses) throws SQLException, ClassNotFoundException {
        this.props = properties;
        this.connection = getConnection(properties);
        this.metaClasses = metaClasses;
    }

    private Connection getConnection(Properties properties) throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }
        String driverName = properties.getProperty("driverName");
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Class.forName(driverName);
        connection = DriverManager.getConnection(url, username, password);

        return connection;
    }

    public EntityMeta getEntityMetaByClass(Class clazz) throws Exception {
        if (!isAnnoteted(clazz)) {
            throw new Exception(clazz.getName() + " was not annotated");
        }
        Optional<EntityMeta> optional = metaClasses.stream().filter((e) -> e.getName().equals(getClassName(clazz))).findFirst();
        if (!optional.isPresent()) {
            throw new Exception(clazz.getName() + " was not scanned");
        }
        return optional.get();
    }

    @Override
    public void persist(Object obj) {
        try {
            if (obj != null && isAnnoteted(obj.getClass()) && !this.managedObjects.contains(obj)) {
                this.connection = getConnection(this.props);
                EntityMeta entityMeta = getEntityMetaByClass(obj.getClass());
                String sql = "insert into " + entityMeta.getName() + " (";
                Method idSetter = null;
                String idType = null;
                List<String> insertedVal = new ArrayList<>();
                for (Attribute attribute : entityMeta.getAttributes()) {
                    if (attribute.getType() == Attribute.Type.withoutConnection) {
                        if (attribute.getName().equals("id")) {
                            idSetter = attribute.getSetter();
                            idType = attribute.getRightSide();
                        } else {
                            sql = sql + attribute.getName().toLowerCase() + ",";
                            String str = attribute.getGetter().invoke(obj, null).toString();
                            if (attribute.getRightSide().equals("String") || attribute.getRightSide().equals("Date")) {
                                str = "'" + str + "'";
                            }
                            insertedVal.add(str);
                        }
                    }
                }
                if (insertedVal.size() > 0) {
                    sql = sql.substring(0, sql.length() - 1) + ") values (";
                    for (String val : insertedVal) {
                        sql = sql + val + ",";
                    }
                    sql = sql.substring(0, sql.length() - 1) + ");";
                } else {
                    sql = "insert into " + entityMeta.getName() + " default values;";
                }
                PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.executeUpdate();
                if (idSetter == null) {
                    throw new Exception("Entity does not have id column");
                }
                ResultSet keys = statement.getGeneratedKeys();
                keys.next();
                switch (idType) {
                    case "String":
                        idSetter.invoke(obj, keys.getString("id"));
                        break;
                    case "long":
                    case "Long":
                        idSetter.invoke(obj, keys.getLong("id"));
                        break;
                    case "int":
                    case "Integer":
                        idSetter.invoke(obj, keys.getInt("id"));
                        break;
                    case "double":
                    case "Double":
                        idSetter.invoke(obj, keys.getDouble("id"));
                        break;
                    case "float":
                    case "Float":
                        idSetter.invoke(obj, keys.getFloat("id"));
                        break;
                    case "Date":
                        idSetter.invoke(obj, keys.getDate("id"));
                        break;
                }
                this.managedObjects.add(obj);
                String relationTable = null;
                for (Attribute attribute : entityMeta.getAttributes()) {
                    if (attribute.getType() != Attribute.Type.withoutConnection) {
                        switch (attribute.getType()) {
                            case OneToOne:
                                relationTable = ((OneToOne) obj.getClass().getDeclaredField(attribute.getName()).getAnnotation(OneToOne.class)).tableName();
                                break;
                            case ManyToMany:
                                relationTable = ((ManyToMany) obj.getClass().getDeclaredField(attribute.getName()).getAnnotation(ManyToMany.class)).tableName();
                                break;
                            case OneToMany:
                                relationTable = attribute.getLeftSide().toLowerCase() + "_" + attribute.getRightSide().toLowerCase();
                                break;
                            case ManyToOne:
                                relationTable = attribute.getRightSide().toLowerCase() + "_" + attribute.getLeftSide().toLowerCase();
                                break;
                        }
                        if (attribute.getType()==Attribute.Type.OneToOne || attribute.getType()==Attribute.Type.ManyToOne){
                            Object rightSide=attribute.getGetter().invoke(obj, null);
                            attribute.getSetter().invoke(obj, createConnection(obj, rightSide, attribute, relationTable));
                        }else{
                            List rideSideObjs=(List)attribute.getGetter().invoke(obj, null);//!!
                            List newRideSideObjs=new ArrayList();
                            if(rideSideObjs!=null){
                                for (Object rightSide:rideSideObjs)
                                    newRideSideObjs.add(createConnection(obj, rightSide, attribute, relationTable));//!!!
                            }
                            attribute.getSetter().invoke(obj, newRideSideObjs);
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    private Object createConnection(Object leftSide,Object rightSide,Attribute attribute,String relationTable) throws Exception{
        EntityMeta leftSideEntityMeta=getEntityMetaByClass(leftSide.getClass());
        EntityMeta rightSideEntityMeta=getEntityMetaByClass(rightSide.getClass());
        String rightSideId=getIdFromEntity(rightSide, rightSideEntityMeta);
        String leftSideId=getIdFromEntity(leftSide, leftSideEntityMeta);
        Object storedObject=null;
        if (rightSideId!=null)
            storedObject=find(rightSide.getClass(), rightSideId);
        if (storedObject==null)
        {
            persist(rightSide);
            storedObject=rightSide;
            rightSideId=getIdFromEntity(storedObject, rightSideEntityMeta);
        }
        if (!isExistEntityConnection(relationTable,leftSideId,rightSideId,leftSideEntityMeta.getName(),attribute.getRightSide()))
            createConnectionInDB(relationTable,leftSideId,rightSideId,leftSideEntityMeta.getName(),attribute.getRightSide());
        return storedObject;
    }
    
    private void createConnectionInDB(String relationTable,String leftSideId,String rightSideId,String leftSideEntityName,String rightSideEntityName) throws SQLException, ClassNotFoundException{
        this.connection=getConnection(this.props);
        String sql="insert into "+relationTable+" ("+leftSideEntityName.toLowerCase()+"_id,"+rightSideEntityName.toLowerCase()+"_id) values ("+leftSideId+","+rightSideId+");";
        PreparedStatement statement=this.connection.prepareStatement(sql);
        statement.executeUpdate();
    }
    
    private boolean isExistEntityConnection(String relationTable,String leftSideId,String rightSideId,String leftSideEntityName,String rightSideEntityName) throws SQLException, ClassNotFoundException{
        this.connection=getConnection(this.props);
        String sql="select count(*) from "+relationTable+" where "+leftSideEntityName.toLowerCase()+"_id="+leftSideId+" and "+rightSideEntityName.toLowerCase()+"_id="+rightSideId+";";
        PreparedStatement statement=this.connection.prepareStatement(sql);
        ResultSet rs=statement.executeQuery();
        rs.next();
        long count=rs.getLong("count");
        return count>0;
    }

    private boolean isAnnoteted(Class clazz) {
        Entity entityAnnotation = (Entity) clazz.getAnnotation(Entity.class);
        return entityAnnotation != null;
    }

    @Override
    public <T> T merge(T obj) {
        try {
            if (obj != null && isAnnoteted(obj.getClass()) && this.managedObjects.contains(obj)) {
                this.connection = getConnection(this.props);
                EntityMeta entityMeta = getEntityMetaByClass(obj.getClass());
                String sql = "update " + entityMeta.getName().toLowerCase() + " set ";
                String id=getIdFromEntity(obj, entityMeta);
                String insertedVal=null;
                for (Attribute attribute : entityMeta.getAttributes()) {
                    if (attribute.getType() == Attribute.Type.withoutConnection) {
                        if (!attribute.getName().equals("id")) {
                            insertedVal = attribute.getGetter().invoke(obj, null).toString();
                            if (attribute.getRightSide().equals("String") || attribute.getRightSide().equals("Date")) {
                                insertedVal = "'" + insertedVal + "'";
                            }
                            sql = sql + attribute.getName().toLowerCase() + "="+insertedVal+",";
                        }
                    }
                }
                if (insertedVal!=null) {
                    sql = sql.substring(0, sql.length() - 1);
                    sql = sql+" where id="+id+";";
                    PreparedStatement statement = this.connection.prepareStatement(sql);
                    statement.executeUpdate();
                }
                String relationTable = null;
                for (Attribute attribute : entityMeta.getAttributes()) {
                    if (attribute.getType() != Attribute.Type.withoutConnection) {
                        switch (attribute.getType()) {
                            case OneToOne:
                                relationTable = ((OneToOne) obj.getClass().getDeclaredField(attribute.getName()).getAnnotation(OneToOne.class)).tableName();
                                break;
                            case ManyToMany:
                                relationTable = ((ManyToMany) obj.getClass().getDeclaredField(attribute.getName()).getAnnotation(ManyToMany.class)).tableName();
                                break;
                            case OneToMany:
                                relationTable = attribute.getLeftSide().toLowerCase() + "_" + attribute.getRightSide().toLowerCase();
                                break;
                            case ManyToOne:
                                relationTable = attribute.getRightSide().toLowerCase() + "_" + attribute.getLeftSide().toLowerCase();
                                break;
                        }
                        deleteConnections(id, relationTable,entityMeta.getName().toLowerCase());
                        if (attribute.getType()==Attribute.Type.OneToOne || attribute.getType()==Attribute.Type.ManyToOne){
                            Object rightSide=attribute.getGetter().invoke(obj, null);
                            attribute.getSetter().invoke(obj, createConnection(obj, rightSide, attribute, relationTable));
                        }else{
                            List rideSideObjs=(List)attribute.getGetter().invoke(obj, null);//!!
                            List newRideSideObjs=new ArrayList();
                            if (rideSideObjs!=null){
                                for (Object rightSide:rideSideObjs)
                                    newRideSideObjs.add(createConnection(obj, rightSide, attribute, relationTable));
                            }
                            attribute.getSetter().invoke(obj, newRideSideObjs);
                        }
                    }
                }
                return obj;
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void remove(Object objectForDelete) {
        try {
            if (managedObjects.contains(objectForDelete)) {
                EntityMeta entityMetaForDelete = getEntityMetaByClass(objectForDelete.getClass());
                String id = getIdFromEntity(objectForDelete, entityMetaForDelete);
                for (EntityMeta managedEntityMeta : this.metaClasses) {
                    for (Attribute attribute : managedEntityMeta.getAttributes()) {
                        if (attribute.getType() != Attribute.Type.withoutConnection) {
                            String relationTable = null;
                            if (managedEntityMeta.getName().equals(entityMetaForDelete.getName())) {//случай когда попавшаяся сущность=сущностью удаляемого объекта
                                switch (attribute.getType()) {
                                    case OneToOne:
                                        relationTable = ((OneToOne) objectForDelete.getClass().getDeclaredField(attribute.getName()).getAnnotation(OneToOne.class)).tableName();
                                        break;
                                    case ManyToMany:
                                        relationTable = ((ManyToMany) objectForDelete.getClass().getDeclaredField(attribute.getName()).getAnnotation(ManyToMany.class)).tableName();
                                        break;
                                    case OneToMany:
                                        relationTable = attribute.getLeftSide().toLowerCase() + "_" + attribute.getRightSide().toLowerCase();
                                        break;
                                    case ManyToOne:
                                        relationTable = attribute.getRightSide().toLowerCase() + "_" + attribute.getLeftSide().toLowerCase();
                                        break;
                                }
                                deleteConnections(id, relationTable, attribute.getLeftSide().toLowerCase());
                            } else {
                                if (attribute.getRightSide().equals(entityMetaForDelete.getName())) {
                                    Class clazz = Class.forName(managedEntityMeta.getType());
                                    switch (attribute.getType()) {
                                        case OneToOne:
                                            relationTable = ((OneToOne) clazz.getDeclaredField(attribute.getName()).getAnnotation(OneToOne.class)).tableName();
                                            break;
                                        case ManyToMany:
                                            relationTable = ((ManyToMany) clazz.getDeclaredField(attribute.getName()).getAnnotation(ManyToMany.class)).tableName();
                                            break;
                                        case OneToMany:
                                            relationTable = attribute.getLeftSide().toLowerCase() + "_" + attribute.getRightSide().toLowerCase();
                                            break;
                                        case ManyToOne:
                                            relationTable = attribute.getRightSide().toLowerCase() + "_" + attribute.getLeftSide().toLowerCase();
                                            break;
                                    }
                                    deleteConnections(id, relationTable, attribute.getRightSide().toLowerCase());
                                }
                            }
                        }
                    }
                }
                deleteFromDB(id, entityMetaForDelete);
                deleteFromManagedClass(objectForDelete, entityMetaForDelete);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteConnections(String leftSideId, String tableName, String entityName) throws SQLException, ClassNotFoundException {//удаление связей, которые однонаправленно связывают сущность с другой сущьностью (удаление спомощью атрибута с точки зрения связи)
        this.connection = getConnection(this.props);
        String sql = "delete from " + tableName + " where " + entityName + "_id=" + leftSideId + ";";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeLargeUpdate();
    }

    private void deleteFromDB(String id, EntityMeta entityMeta) throws SQLException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.connection = getConnection(this.props);
        String sql = "delete from " + entityMeta.getName().toLowerCase() + " where id=" + id + ";";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeLargeUpdate();
    }

    public void deleteFromManagedClass(Object deletedObject, EntityMeta deletedEntityMeta) throws Exception {
        this.managedObjects.remove(deletedObject);
        for (Object obj : this.managedObjects) {
            EntityMeta entityMeta = getEntityMetaByClass(obj.getClass());
            for (Attribute attribute : entityMeta.getAttributes()) {
                if (attribute.getType() != Attribute.Type.withoutConnection && attribute.getRightSide().toLowerCase().equals(deletedEntityMeta.getName().toLowerCase())) {//getType?getName
                    if (attribute.getType() == Attribute.Type.OneToOne && attribute.getGetter().invoke(obj, null).equals(deletedObject)) {
                        Object nullObject = null;
                        attribute.getSetter().invoke(obj, nullObject);//если не передавать объект то будет использован дефолтный конструктор
                    } else {
                        List list = (List) (attribute.getGetter().invoke(obj, null));
                        list.remove(deletedObject);
                    }
                }
            }
        }
    }

    private String getIdFromEntity(Object object, EntityMeta entityMeta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object id = null;
        String strId=null;
        if (this.managedObjects.contains(object)){
            for (Attribute attribute : entityMeta.getAttributes()) {
                if (attribute.getName().equals("id")) {
                    id = attribute.getGetter().invoke(object, null);
                    if (id==null)
                        return null;
                    strId= id.toString();
                    break;
                }
            }
            if (strId!=null && !isNum(strId)) {
                strId = "'" + strId + "'";
            }
        }
        return strId;
    }

    @Override
    public <T> T refresh(T obj) {//обновляем указанный объект и его всязи (если сущности связи нет подгружаем её); сущности по другой конец связи не обнавляються!!!
        try{
            if (obj==null || !isAnnoteted(obj.getClass()))
                throw new Exception("unacceptable object");
            EntityMeta entityMeta=getEntityMetaByClass(obj.getClass());
            String id=getIdFromEntity(obj, entityMeta);
            Object storedObject = findManagedObjectById(id, obj.getClass());
            if (storedObject!=null){
                this.connection = getConnection(this.props);
                String sql = "Select * from " + entityMeta.getName().toLowerCase() + " where id=" + id + ";";
                PreparedStatement statement = this.connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                return updateObject(resultSet, obj, entityMeta);
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    private <T> T updateObject(ResultSet resultSet,T obj,EntityMeta entityMeta) throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException, Exception{
        if (resultSet.isClosed() || !resultSet.next())
        {
            deleteFromManagedClass(obj, entityMeta);
            throw new SQLException("ResultSet for " + obj.getClass().getName() + " was closed");
        }
        for (Attribute attribute : entityMeta.getAttributes()) {
            if (attribute.getType() == Attribute.Type.withoutConnection) {
                switch (attribute.getRightSide()) {
                    case "String":
                        attribute.getSetter().invoke(obj, resultSet.getString(attribute.getName()));
                        break;
                    case "long":
                    case "Long":
                        attribute.getSetter().invoke(obj, resultSet.getLong(attribute.getName()));
                        break;
                    case "int":
                    case "Integer":
                        attribute.getSetter().invoke(obj, resultSet.getInt(attribute.getName()));
                        break;
                    case "double":
                    case "Double":
                        attribute.getSetter().invoke(obj, resultSet.getDouble(attribute.getName()));
                        break;
                    case "float":
                    case "Float":
                        attribute.getSetter().invoke(obj, resultSet.getFloat(attribute.getName()));
                        break;
                    case "Date":
                        attribute.getSetter().invoke(obj, resultSet.getDate(attribute.getName()));
                        break;
                }
            }
        }
        for (Attribute attribute : entityMeta.getAttributes()) {
            if (attribute.getType() != Attribute.Type.withoutConnection) {
                String relationTable = null;
                Class rightSideClass = null;
                String objId = resultSet.getString("id");
                switch (attribute.getType()) {
                    case OneToOne:
                        OneToOne oneToOneAnnotation = (OneToOne) obj.getClass().getDeclaredField(attribute.getName()).getAnnotation(OneToOne.class);
                        relationTable = oneToOneAnnotation.tableName();
                        rightSideClass = attribute.getGetter().getReturnType();
                        break;
                    case ManyToMany:
                        ManyToMany manyToMany = (ManyToMany) obj.getClass().getDeclaredField(attribute.getName()).getAnnotation(ManyToMany.class);
                        relationTable = manyToMany.tableName();
                        rightSideClass = ((Class) ((ParameterizedType) attribute.getGetter().getGenericReturnType()).getActualTypeArguments()[0]);
                        break;
                    case OneToMany:
                        relationTable = attribute.getLeftSide().toLowerCase() + "_" + attribute.getRightSide().toLowerCase();
                        rightSideClass = ((Class) ((ParameterizedType) attribute.getGetter().getGenericReturnType()).getActualTypeArguments()[0]);
                        break;
                    case ManyToOne:
                        relationTable = attribute.getRightSide().toLowerCase() + "_" + attribute.getLeftSide().toLowerCase();
                        rightSideClass = attribute.getGetter().getReturnType();
                        break;
                }
                if (attribute.getType() == Attribute.Type.OneToOne || attribute.getType() == Attribute.Type.ManyToOne) {
                    List connections = getConnectionByAttridute(rightSideClass, relationTable, objId, attribute);
                    Object connection = null;
                    if (connections.size() > 0) {
                        connection = connections.get(0);
                    }
                    attribute.getSetter().invoke(obj, connection);
                } else {
                    attribute.getSetter().invoke(obj, getConnectionByAttridute(rightSideClass, relationTable, objId, attribute));
                }
            }
        }
        return obj;
    }

    @Override
    public <T> T find(Class<T> clazz, Object idVal) {
        try {
            T obj = findManagedObjectById(idVal, clazz);
            if (obj != null) {
                return obj;
            }
            EntityMeta entityMeta = getEntityMetaByClass(clazz);
            this.connection = getConnection(this.props);
            String id = idVal.toString();
            if (idVal instanceof String) {
                id = "'" + id + "'";
            }
            String sql = "Select * from " + entityMeta.getName().toLowerCase() + " where id=" + id + ";";
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            return createOject(clazz, entityMeta, resultSet);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private <T> T createOject(Class<T> clazz, EntityMeta entityMeta, ResultSet resultSet) throws NoSuchMethodException, SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchFieldException {
        T obj = clazz.getDeclaredConstructor(null).newInstance(null);
        if (resultSet.isClosed() || !resultSet.next())//perhaps add next for rs
        {
            throw new SQLException("ResultSet for " + clazz.getName() + " was closed");
        }
        for (Attribute attribute : entityMeta.getAttributes()) {
            if (attribute.getType() == Attribute.Type.withoutConnection) {
                switch (attribute.getRightSide()) {
                    case "String":
                        attribute.getSetter().invoke(obj, resultSet.getString(attribute.getName()));
                        break;
                    case "long":
                    case "Long":
                        attribute.getSetter().invoke(obj, resultSet.getLong(attribute.getName()));
                        break;
                    case "int":
                    case "Integer":
                        attribute.getSetter().invoke(obj, resultSet.getInt(attribute.getName()));
                        break;
                    case "double":
                    case "Double":
                        attribute.getSetter().invoke(obj, resultSet.getDouble(attribute.getName()));
                        break;
                    case "float":
                    case "Float":
                        attribute.getSetter().invoke(obj, resultSet.getFloat(attribute.getName()));
                        break;
                    case "Date":
                        attribute.getSetter().invoke(obj, resultSet.getDate(attribute.getName()));
                        break;
                }
            }
        }
        this.managedObjects.add(obj);
        for (Attribute attribute : entityMeta.getAttributes()) {
            if (attribute.getType() != Attribute.Type.withoutConnection) {
                String relationTable = null;
                Class rightSideClass = null;
                String objId = resultSet.getString("id");
                switch (attribute.getType()) {
                    case OneToOne:
                        OneToOne oneToOneAnnotation = (OneToOne) clazz.getDeclaredField(attribute.getName()).getAnnotation(OneToOne.class);
                        relationTable = oneToOneAnnotation.tableName();
                        rightSideClass = attribute.getGetter().getReturnType();
                        break;
                    case ManyToMany:
                        ManyToMany manyToMany = (ManyToMany) clazz.getDeclaredField(attribute.getName()).getAnnotation(ManyToMany.class);
                        relationTable = manyToMany.tableName();
                        rightSideClass = ((Class) ((ParameterizedType) attribute.getGetter().getGenericReturnType()).getActualTypeArguments()[0]);
                        break;
                    case OneToMany:
                        relationTable = attribute.getLeftSide().toLowerCase() + "_" + attribute.getRightSide().toLowerCase();
                        rightSideClass = ((Class) ((ParameterizedType) attribute.getGetter().getGenericReturnType()).getActualTypeArguments()[0]);
                        break;
                    case ManyToOne:
                        relationTable = attribute.getRightSide().toLowerCase() + "_" + attribute.getLeftSide().toLowerCase();
                        rightSideClass = attribute.getGetter().getReturnType();
                        break;
                }
                if (attribute.getType() == Attribute.Type.OneToOne || attribute.getType() == Attribute.Type.ManyToOne) {
                    List connections = getConnectionByAttridute(rightSideClass, relationTable, objId, attribute);
                    Object connection = null;
                    if (connections.size() > 0) {
                        connection = connections.get(0);
                    }
                    attribute.getSetter().invoke(obj, connection);
                } else {
                    attribute.getSetter().invoke(obj, getConnectionByAttridute(rightSideClass, relationTable, objId, attribute));
                }
            }
        }
        return obj;
    }

    private <T> List<T> getConnectionByAttridute(Class<T> returnClass, String relationTable, String leftSideId, Attribute leftSide) throws SQLException, ClassNotFoundException {
        List<T> connections = new ArrayList<>();
        String rightSideName = leftSide.getRightSide().toLowerCase();
        this.connection = getConnection(this.props);
        String sql = "Select " + rightSideName + "_id" + " from " + relationTable + " where " + leftSide.getLeftSide().toLowerCase() + "_id=";
        if (!isNum(leftSideId)) {
            leftSideId = "'" + leftSideId + "'";
        }
        sql = sql + leftSideId + ";";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Object rightSideId = rs.getObject(rightSideName + "_id");
            T connection = find(returnClass, rightSideId);//!!!можно сделать рекурсию с refresh и списком обновлённых элементов
            if (connection != null) {
                connections.add(connection);
            }
        }
        return connections;
    }

    private <T> T findManagedObjectById(Object id, Class<T> clazz) {
        try {
            for (Object obj : this.managedObjects) {
                EntityMeta entityMeta = getEntityMetaByClass(obj.getClass());
                for (Attribute attribute : entityMeta.getAttributes()) {
                    if (clazz.isInstance(obj) && attribute.getName().equals("id") && attribute.getGetter().invoke(obj, null).toString().equals(id.toString())) {
                        return (T) obj;
                    }
                }
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private boolean isNum(String id) {
        try {
            long numId = Long.parseLong(id);
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    private String getClassName(Class clazz) {
        Entity EntityAnnotation = (Entity) (clazz.getAnnotation(Entity.class));
        if (EntityAnnotation != null && !EntityAnnotation.name().equals("")) {
            return EntityAnnotation.name();
        }
        return clazz.getSimpleName();
    }

}

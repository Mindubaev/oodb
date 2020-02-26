/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask7;

import com.mycompany.entitymanagertask7.Annotations.Column;
import com.mycompany.entitymanagertask7.Annotations.Entity;
import com.mycompany.entitymanagertask7.Annotations.Id;
import com.mycompany.entitymanagertask7.Annotations.ManyToMany;
import com.mycompany.entitymanagertask7.Annotations.ManyToOne;
import com.mycompany.entitymanagertask7.Annotations.OneToMany;
import com.mycompany.entitymanagertask7.Annotations.OneToOne;
import com.mycompany.entitymanagertask7.MetaClass.Attribute;
import com.mycompany.entitymanagertask7.MetaClass.EntityMeta;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Artur
 */
public class CustomJPAAnnotationProcessor {
    
    private String path;

    public CustomJPAAnnotationProcessor(String path) {
        this.path = path;
    }
    
    private List<Class<?>> scanPath(String path){
        try{
        String scannedPath=path.replace(".", "/");
        URL scannedUrl=Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl==null)
            throw new IllegalArgumentException("Package "+path+" does not exist");
        File scannedPackage=new File(scannedUrl.toURI());
        List<Class<?>> classes =new ArrayList<>();
        for (File currentPackage:scannedPackage.listFiles())
        {
            classes.addAll(scanPath(currentPackage,path));
        }
        return classes;
        }
        catch(Exception ex){
            System.err.println(ex);
            return new ArrayList<>();
        }
    }

    private List<Class<?>> scanPath(File currentPackage, String path) {
        List<Class<?>> classes = new ArrayList<>();
        String resource=path+"."+currentPackage.getName();
        if (currentPackage.isDirectory())
        {
            for (File file:currentPackage.listFiles())
                classes.addAll(scanPath(file,resource));
        }
        else
        {
            if (resource.endsWith(".class"))
            {
                String className=resource.substring(0,resource.length()-6);//!!!
                try{
                    classes.add(Class.forName(className));
                }catch(ClassNotFoundException ex){
                    System.err.println("Class with name"+className+" not found");
                }
            }
        }
        return classes;
    }
    
    public List<EntityMeta> builtMetaClasses(){
        List<Class<?>> classes=scanPath(path);
        List<EntityMeta> metaClasses=new ArrayList<>();
        for (Class clazz:classes)
        {
            EntityMeta metaClass=builtMetaClass(clazz);
            if (metaClass!=null)
                metaClasses.add(metaClass);
        }
        return metaClasses;
    }
    
    private EntityMeta builtMetaClass(Class clazz){
        Entity entityAnnotation=(Entity)(clazz.getAnnotation(Entity.class));
            if (entityAnnotation!=null)
            {
                String name=getClassName(clazz);
                String type=clazz.getTypeName();
                List<Attribute> attributes=findAttributes(clazz);
                return new EntityMeta(name,type, attributes);
            }
            else
                return null;
    }
    
    private String getClassName(Class clazz){
        Entity EntityAnnotation=(Entity)(clazz.getAnnotation(Entity.class));
        if (EntityAnnotation!=null && !EntityAnnotation.name().equals(""))
            return EntityAnnotation.name();
        return clazz.getSimpleName();
    }
    
    private String getAttributeName(Field field){
        Column columnAnnotation=(Column)(field.getAnnotation(Column.class));
        Id idAnnotation=(Id)(field.getAnnotation(Id.class));
        if (idAnnotation!=null)
            return "id";
        if (columnAnnotation!=null)
            return columnAnnotation.name();
        return field.getName();
    }
    
    private List<Attribute> extractAttributesFromSuperClass(Class clazz){
        clazz=clazz.getSuperclass();
        if (clazz!=null && !clazz.equals(Object.class))
            return findAttributes(clazz);
        else
            return new ArrayList<>();
    }

    private List<Attribute> findAttributes(Class clazz) {
        List<Attribute> attributes=new ArrayList<>();
        Field[] fields=clazz.getDeclaredFields();
        for (Field field:fields)
        {
            Column columnAnnotation=field.getAnnotation(Column.class);
            Id idAnnotation=field.getAnnotation(Id.class);
            OneToMany oneToManyAnnotation=field.getAnnotation(OneToMany.class);
            ManyToOne manyToOneAnnotation=field.getAnnotation(ManyToOne.class);
            ManyToMany manyToManyAnnotation=field.getAnnotation(ManyToMany.class);
            OneToOne oneToOneAnnotation=field.getAnnotation(OneToOne.class);
            if (columnAnnotation!=null || idAnnotation!=null || oneToManyAnnotation!=null || oneToOneAnnotation!=null || manyToOneAnnotation!=null || manyToManyAnnotation!=null)
            {
                String name=getAttributeName(field);
                Class fieldClass=field.getType();
                String type=getClassName(fieldClass);
                String leftSide=getClassName(clazz);
                String rightSide=type;
                Attribute.Type connectionType=Attribute.Type.withoutConnection;
                if (oneToManyAnnotation!=null)
                {
                   connectionType=Attribute.Type.OneToMany;
                   Class parametrClass=(Class<?>)((ParameterizedType)(field.getGenericType())).getActualTypeArguments()[0];
                   rightSide=getClassName(parametrClass);
                }
                else
                {
                    if (manyToOneAnnotation!=null)
                    {
                        connectionType=Attribute.Type.ManyToOne;
                        rightSide=type;
                    }
                    else
                    {
                        if (manyToManyAnnotation!=null)
                        {
                            connectionType=Attribute.Type.ManyToMany;
                            Class parametrClass=(Class<?>)((ParameterizedType)(field.getGenericType())).getActualTypeArguments()[0];
                            rightSide=getClassName(parametrClass);
                        }
                        else
                        {
                            if (oneToOneAnnotation!=null)
                            {
                                connectionType=Attribute.Type.OneToOne;
                                rightSide=type;
                            }
                        }
                    }
                }
                attributes.add(new Attribute(name, connectionType, leftSide, rightSide));
            }
        }
        attributes.addAll(extractAttributesFromSuperClass(clazz));
        return attributes;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask7.DAO;

import com.mycompany.entitymanagertask7.CustomJPAAnnotationProcessor;
import com.mycompany.entitymanagertask7.MetaClass.EntityMeta;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Artur
 */
public class EntityManagerFactory {
    
    private  List<EntityMeta> metaClasses;
    private Properties props;

    public EntityManagerFactory(Properties properties,String classPath) {
        try{
            this.props=properties;
            CustomJPAAnnotationProcessor annotationProcessor=new CustomJPAAnnotationProcessor(classPath);
            this.metaClasses=annotationProcessor.builtMetaClasses();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public EntityManager create() throws SQLException, ClassNotFoundException{
        switch(props.getProperty("driverName")){
            case "org.postgresql.Driver":
                return new EntityManagerPostgresql(props, metaClasses);
            default:
                return null;
        }
    }
    
}

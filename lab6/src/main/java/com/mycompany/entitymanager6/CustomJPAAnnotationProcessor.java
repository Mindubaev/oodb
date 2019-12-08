/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanager6;

import com.mycompany.entitymanager6.Annotations.Column;
import com.mycompany.entitymanager6.Annotations.Entity;
import com.mycompany.entitymanager6.Annotations.ManyToMany;
import com.mycompany.entitymanager6.Annotations.ManyToOne;
import com.mycompany.entitymanager6.Annotations.OneToMany;
import com.mycompany.entitymanager6.MetaClass.Attribute;
import com.mycompany.entitymanager6.MetaClass.EntityMeta;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
                String name=entityAnnotation.name();
                String type=clazz.getTypeName();
                List<Attribute> attributes=findAttributes(clazz);
                return new EntityMeta(name,type, attributes);
            }
            else
                return null;
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
            if (columnAnnotation!=null)
            {
                String name=columnAnnotation.name();
                String type=field.getType().getName();
                String leftSide=clazz.getTypeName();
                String rightSide=type;
                Attribute.Type connectionType=Attribute.Type.OneToOne;
                OneToMany oneToManyAnnotation=field.getAnnotation(OneToMany.class);
                if (oneToManyAnnotation!=null)
                {
                   connectionType=Attribute.Type.OneToMany;
                   rightSide=type+"<"+oneToManyAnnotation.rightSide().getName()+">";
                }
                else
                {
                    ManyToOne manyToOneAnnotation=field.getAnnotation(ManyToOne.class);
                    if (manyToOneAnnotation!=null)
                    {
                        connectionType=Attribute.Type.ManyToOne;
                        rightSide=manyToOneAnnotation.rightClass().getName();
                    }
                    else
                    {
                        ManyToMany manyToManyAnnotation=field.getAnnotation(ManyToMany.class);
                        if (manyToManyAnnotation!=null)
                        {
                            connectionType=Attribute.Type.ManyToMany;
                            rightSide=type+"<"+manyToManyAnnotation.rightSide().getName()+">";
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

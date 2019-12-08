/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanager6.MetaClass;

import java.util.List;

/**
 *
 * @author Artur
 */
public class EntityMeta {
    
    private String name;
    private String type;
    private List<Attribute> attributes;

    public EntityMeta(String name,String type, List<Attribute> attributes) {
        this.name = name;
        this.attributes = attributes;
        this.type=type;
    }

    public String getName() {
        return name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String info="\r<Entity name=\""+name+"\" type=\""+type+"\" >";
        for (Attribute attribute:attributes)
            info=info+"\r\t"+attribute.toString();
        info=info+"\r"+"<Entity/>";
        return info;
    }
    
}

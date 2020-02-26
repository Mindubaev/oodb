/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask7.MetaClass;

import java.util.HashMap;
import java.util.HashSet;
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
    
    public boolean checkDB(HashMap<String,HashSet<String>> tables){
        if (!tables.containsKey(this.name.toLowerCase()))
            return false;
        HashSet<String> table=tables.get(this.name.toLowerCase());
        for (Attribute attribute:this.attributes)
        {
            if (!checkAttridute(tables,table,attribute))
                return false;
        }
        return true;
    }

    private boolean checkAttridute(HashMap<String,HashSet<String>> tables,HashSet<String> table, Attribute attribute) {
        String rightSideEntityName=attribute.getRightSide().toLowerCase();
        String nameOfRelationTable=attribute.getName().toLowerCase()+"_"+rightSideEntityName;
        if (attribute.getType()==Attribute.Type.withoutConnection && table.contains(attribute.getName().toLowerCase()))
            return true;
        if (attribute.getType()==Attribute.Type.OneToOne && (table.contains(rightSideEntityName+"_id") || (tables.containsKey(rightSideEntityName) && tables.get(rightSideEntityName).contains(this.name.toLowerCase()+"_id"))))
            return true;
        if (attribute.getType()==Attribute.Type.ManyToOne && table.contains(rightSideEntityName+"_id"))
            return true;
        if (attribute.getType()==Attribute.Type.OneToMany && (tables.containsKey(rightSideEntityName) && tables.get(rightSideEntityName).contains(this.name.toLowerCase()+"_id")))
            return true;
        if (attribute.getType()==Attribute.Type.ManyToMany && tables.containsKey(nameOfRelationTable) && tables.get(nameOfRelationTable).contains(this.name.toLowerCase()+"_id") && tables.get(nameOfRelationTable).contains(rightSideEntityName+"_id"))
            return true;
        return false;
    }
    
}

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
        String leftSideRightSideRelationTable=this.name.toLowerCase()+"_"+rightSideEntityName;
        String rightSideLeftSideRelationTable=rightSideEntityName+"_"+this.name.toLowerCase();
        if (attribute.getType()==Attribute.Type.withoutConnection && table.contains(attribute.getName().toLowerCase()))
            return true;
        if  ((attribute.getType()==Attribute.Type.OneToOne || attribute.getType()==Attribute.Type.ManyToMany) && 
                (checkRelationTable(tables, this.name.toLowerCase(), rightSideEntityName, rightSideLeftSideRelationTable)
                || checkRelationTable(tables, this.name.toLowerCase(), rightSideEntityName, leftSideRightSideRelationTable))
            )
            return true;
        if (attribute.getType()==Attribute.Type.ManyToOne && checkRelationTable(tables, this.name.toLowerCase(), rightSideEntityName, rightSideLeftSideRelationTable))
            return true;
        if (attribute.getType()==Attribute.Type.OneToMany && checkRelationTable(tables, this.name.toLowerCase(), rightSideEntityName, leftSideRightSideRelationTable))
            return true;
        return false;
    }
    
    private boolean checkRelationTable(HashMap<String,HashSet<String>> tables,String entity1,String entity2,String tableRelationName){
        return (tables.containsKey(tableRelationName) && tables.get(tableRelationName).contains(entity1+"_id") && tables.get(tableRelationName).contains(entity2+"_id"));
    }
    
}
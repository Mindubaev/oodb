/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask7.MetaClass;

/**
 *
 * @author Artur
 */
public class Attribute {
    
    private String name;
    private Type type;
    private String leftSide;
    private String rightSide;

    public Attribute(String name, Type type, String leftSide, String rightSide) {
        this.name = name;
        this.type = type;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getLeftSide() {
        return leftSide;
    }

    public String getRightSide() {
        return rightSide;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setLeftSide(String leftSide) {
        this.leftSide = leftSide;
    }

    public void setRightSide(String rightSide) {
        this.rightSide = rightSide;
    }

    @Override
    public String toString() {
        return "<Attribute name=\""+name+"\" leftSide=\""+leftSide+"\" ConnectionType=\""+type.toString()+"\" rightSide=\""+rightSide+"\" />";
    }
    
    public static enum Type{
        withoutConnection{
            @Override
            public String toString() {
                return "withoutConnection";
            }
        
        }
        ,OneToMany{
            @Override
            public String toString() {
                return "OneToMany";
            }
        
        },ManyToOne{
            @Override
            public String toString() {
                return "ManyToOne";
            }
 
        },ManyToMany{
            @Override
            public String toString() {
                return "ManyToMany";
            }
        
        },OneToOne{
            @Override
            public String toString() {
                return "OneToOne";
            }
        
        }
    }
    
}

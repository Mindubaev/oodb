/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask2;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author ArRMindubaev
 */
@JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY,use = JsonTypeInfo.Id.CLASS,property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Dev.class,name = "dev"),
    @JsonSubTypes.Type(value = Director.class,name = "director")
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "fullName")
public abstract class Human {

    private String fullName ;
    private String birthDate;
    private int salary;
    @JsonBackReference(value = "group-human")
    private Group group;
    
    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the salary
     */
    public int getSalary() {
        return salary;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }
    
}

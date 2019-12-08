/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanager6.Entities;

import com.mycompany.entitymanager6.Annotations.Column;
import com.mycompany.entitymanager6.Annotations.Entity;


/**
 *
 * @author ArRMindubaev
 */
@Entity(name = "human")
public abstract class Human {

    @Column(name = "fullName")
    private String fullName ;
    @Column(name = "birthDate")
    private String birthDate;
    @Column(name = "salary")
    private int salary;

    public Human(String fullName, String birthDate, int salary) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.salary = salary;
    }
    
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

    /**
     * @param salary the salary to set
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }
    
}

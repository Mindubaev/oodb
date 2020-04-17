/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EntityManagerTask10.Entities;

import java.util.Date;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;


/**
 *
 * @author ArRMindubaev
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Human {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "humanGeneratot")
    @SequenceGenerator(name = "humanGeneratot",sequenceName = "human_seq",allocationSize = 1,initialValue = 1)
    private Long id;
    @Column
    private String fullName ;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    @Column
    private int salary;

    public Human(String fullName, Date birthDate, int salary) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.salary = salary;
    }

    public Human() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
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
    
    @Override
    public String toString(){
        String info="";
        String name="";
        if (getFullName()!=null)
            name=getFullName();
        String birthDate="";
        if (getBirthDate()!=null)
            birthDate=getBirthDate().toString();
        return "ROW('"+name+"',"+getSalary()+",'"+birthDate+"',"+info+")";
    }
    
}

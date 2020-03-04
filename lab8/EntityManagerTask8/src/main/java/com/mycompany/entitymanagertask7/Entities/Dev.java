/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask7.Entities;

import com.mycompany.entitymanagertask7.Annotations.Column;
import com.mycompany.entitymanagertask7.Annotations.Entity;
import com.mycompany.entitymanagertask7.Annotations.Id;
import com.mycompany.entitymanagertask7.Annotations.OneToOne;


/**
 *
 * @author ArRMindubaev
 */
@Entity(name="Dev")
public class Dev extends Human{
    
    @Column(name = "computer")
    @OneToOne(tableName = "dev_computer")
    private Computer computer;
    @Id
    private Long id;

    public Dev(Computer computer, String fullName, String birthDate, int salary) {
        super(fullName, birthDate, salary);
        this.computer = computer;
    }

    public Dev() {
        super(null, null, 0);
    }

    /**
     * @return the computer
     */
    public Computer getComputer() {
        return computer;
    }

    /**
     * @param computer the computer to set
     */
    public void setComputer(Computer computer) {
        this.computer = computer;
    }
    
    @Override
    public String toString(){
        String info="";
        if (getComputer()!=null)
            info=getComputer().toString();
        String name="";
        if (getFullName()!=null)
            name=getFullName();
        String birthDate="";
        if (getBirthDate()!=null)
            birthDate=getBirthDate();
        return "ROW('"+name+"',"+getSalary()+",'"+birthDate+"',"+info+")";
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}

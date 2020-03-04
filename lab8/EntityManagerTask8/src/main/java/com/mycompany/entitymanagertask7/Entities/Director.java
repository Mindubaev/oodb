/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask7.Entities;

import com.mycompany.entitymanagertask7.Annotations.Column;
import com.mycompany.entitymanagertask7.Annotations.Entity;
import com.mycompany.entitymanagertask7.Annotations.Id;

/**
 *
 * @author ArRMindubaev
 */
@Entity(name = "Director")
public class Director extends Human{
    
    @Id
    private Long id;

    public Director( String fullName, String birthDate, int salary) {
        super(fullName, birthDate, salary);
        this.id = id;
    }

    public Director(Long id, String fullName, String birthDate, int salary) {
        super(fullName, birthDate, salary);
        this.id = id;
    }

    public Director() {
        super(null, null, 0);
    }
     
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public String toString(){
        String info="";
        String name="";
        if (getFullName()!=null)
            name=getFullName();
        String birthDate="";
        if (getBirthDate()!=null)
            birthDate=getBirthDate();
        return "ROW('"+name+"',"+getSalary()+",'"+birthDate+"',"+info+")";
    }
    
}

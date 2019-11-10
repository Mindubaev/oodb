/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask5.Entities;

/**
 *
 * @author ArRMindubaev
 */
public class Director extends Human{
    
    private Computer computer;

    public Director(String fullName, String birthDate, int salary,Computer computer) {
        super(fullName, birthDate, salary);
        this.computer=computer;
    }

    public Computer getComputer() {
        return computer;
    }

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
    
}

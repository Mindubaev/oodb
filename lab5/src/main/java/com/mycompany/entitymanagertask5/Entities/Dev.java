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
public class Dev extends Human{
    
    private Computer computer;

    public Dev(Computer computer, String fullName, String birthDate, int salary) {
        super(fullName, birthDate, salary);
        this.computer = computer;
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
    
}

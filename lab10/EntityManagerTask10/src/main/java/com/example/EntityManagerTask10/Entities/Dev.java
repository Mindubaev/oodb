/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EntityManagerTask10.Entities;

import java.util.Date;
import javax.persistence.*;


/**
 *
 * @author ArRMindubaev
 */
@Entity
public class Dev extends Human{
    
    @OneToOne
    private Computer computer;

    public Dev(Computer computer, String fullName, Date birthDate, int salary) {
        super(fullName, birthDate, salary);
        this.computer = computer;
    }

    public Dev() {
        super();
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
    
}

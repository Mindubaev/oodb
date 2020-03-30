/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask9.Entities;

import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author ArRMindubaev
 */
@Entity
public class Server {
    
    @Id
    private Long id;
    @Column
    private String description;

    public Server() {
    }

    public Server(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Server(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString(){
        String info="";
        if (getDescription()!=null)
            info=getDescription();
        return "ROW('"+info+"')";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}

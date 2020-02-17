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
@Entity(name = "TechQuest")
public class TechQuest {
    
    @Id
    private Long id;
    @Column(name = "description")
    private String description;

    public TechQuest() {
    }

    public TechQuest(Long id, String description) {
        this.id = id;
        this.description = description;
    } 
    
    /**
     * @return the description
     */
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

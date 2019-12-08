/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanager6.Entities;

import com.mycompany.entitymanager6.Annotations.Column;
import com.mycompany.entitymanager6.Annotations.Entity;
import com.mycompany.entitymanager6.Annotations.ManyToMany;
import java.util.Set;

/**
 *
 * @author ArRMindubaev
 */
@Entity(name = "Server")
public class Server {
    
    @Column(name = "description")
    private String description;
    @Column(name = "groups")
    @ManyToMany(rightSide = Group.class)
    private Set<Group> groups;

    public Server() {
    }

    public Server(String description, Set<Group> groups) {
        this.description = description;
        this.groups = groups;
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

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
    
}

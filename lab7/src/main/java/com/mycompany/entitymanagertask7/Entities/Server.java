/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask7.Entities;

import java.util.Set;
import com.mycompany.entitymanagertask7.Annotations.Column;
import com.mycompany.entitymanagertask7.Annotations.Entity;
import com.mycompany.entitymanagertask7.Annotations.Id;
import com.mycompany.entitymanagertask7.Annotations.ManyToOne;
import com.mycompany.entitymanagertask7.Annotations.OneToMany;

/**
 *
 * @author ArRMindubaev
 */
@Entity(name = "Server")
public class Server {
    
    @Id
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "team")
    @ManyToOne(rightClass = Team.class)
    private Team team;

    public Server() {
    }

    public Server(Long id, String description, Team team) {
        this.id = id;
        this.description = description;
        this.team = team;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}

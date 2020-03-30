/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask9.Entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ArRMindubaev
 */
@Entity
public class Team {

    @Id
    private long id;
    @OneToOne
    private Director director;
    @OneToMany
    private List<Dev> empls;
    @OneToMany
    private List<Server> servers;
    @OneToMany
    private List<Project> projects;

    public Team() {
    }
    
    public List<Server> getServers() {
        return servers;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    
    public List<Dev> getEmpls() {
        return empls;
    }

    /**
     * @param empls the empls to set
     */
    public void setEmpls(List<Dev> empls) {
        this.empls = empls;
    }
    
    public void addDev(Dev dev){
        empls.add(dev);
    } 

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}

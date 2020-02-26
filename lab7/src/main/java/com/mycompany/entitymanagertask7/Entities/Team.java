/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask7.Entities;

import com.mycompany.entitymanagertask7.Annotations.Column;
import com.mycompany.entitymanagertask7.Annotations.Entity;
import com.mycompany.entitymanagertask7.Annotations.Id;
import com.mycompany.entitymanagertask7.Annotations.ManyToOne;
import com.mycompany.entitymanagertask7.Annotations.OneToMany;
import com.mycompany.entitymanagertask7.Annotations.OneToOne;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ArRMindubaev
 */
@Entity(name = "Team")
public class Team {

    @Id
    private long id;
    @OneToOne(rightSide = Director.class)
    private Director director;
    @OneToMany(rightSide = Dev.class)
    private List<Dev> empls;
    @OneToMany(rightSide = Server.class)
    private Set<Server> servers;
    @OneToMany(rightSide = Project.class)
    private List<Project> projects;

    public Team() {
    }
    
    public Set<Server> getServers() {
        return servers;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setServers(Set<Server> servers) {
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

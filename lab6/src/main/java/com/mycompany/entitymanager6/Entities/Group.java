/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanager6.Entities;

import com.mycompany.entitymanager6.Annotations.Column;
import com.mycompany.entitymanager6.Annotations.Entity;
import com.mycompany.entitymanager6.Annotations.ManyToMany;
import com.mycompany.entitymanager6.Annotations.ManyToOne;
import com.mycompany.entitymanager6.Annotations.OneToMany;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ArRMindubaev
 */
@Entity(name = "Group")
public class Group {

    @Column(name = "id")
    private long id;
    @Column(name = "director")
    private Director director;
    @Column(name = "empls")
    @OneToMany(rightSide = Dev.class)
    private List<Dev> empls;
    @Column(name = "server")
    @ManyToMany(rightSide = Server.class)
    private Set<Server> servers;
    @Column(name = "project")
    @ManyToOne(rightClass = Project.class)
    private List<Project> projects;

    public Group() {
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

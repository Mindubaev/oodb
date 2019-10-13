/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;

/**
 *
 * @author ArRMindubaev
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Group {

    public int id;
    private Director director;
    @JsonManagedReference(value = "group-human")
    private List<Human> empls;
    private Server server;
    private Project project;

    public Group() {
    }

    public Group(int id, Director director, List<Human> empls, Server server, Project project) {
        this.id = id;
        this.director = director;
        this.empls = empls;
        this.server = server;
        this.project = project;
    }
    
    /**
     * @return the empls
     */
    public List<Human> getEmpls() {
        return empls;
    }

    /**
     * @param empls the empls to set
     */
    public void setEmpls(List<Human> empls) {
        this.empls = empls;
    }
    
    public void addDev(Dev dev){
        empls.add(dev);
    } 

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Server getServer() {
        return server;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public void setServer(Server server) {
        this.server = server;
    }
    
}

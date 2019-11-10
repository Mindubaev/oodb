/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask5.Entities;

import java.util.List;

/**
 *
 * @author ArRMindubaev
 */
public class Group {

    private long id;
    private Director director;
    private List<Dev> empls;
    private Server server;
    private Project project;

    public Group() {
    }

    public Group(long id,Director director, List<Dev> empls, Server server, Project project) {
        this.id = id;
        this.director = director;
        this.empls = empls;
        this.server = server;
        this.project = project;
        this.id=id;
    }
    
    public Group(Director director, List<Dev> empls, Server server, Project project) {
        this.id = id;
        this.director = director;
        this.empls = empls;
        this.server = server;
        this.project = project;
    }
    
    /**
     * @return the empls
     */
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    @Override
    public String toString(){
        String directorInfo="";
        if (getDirector()!=null)
            directorInfo=getDirector().toString();
        String projectInfo="";
        if (getProject()!=null)
            projectInfo=getProject().toString();
        String serverInfo="";
        if (getServer()!=null)
            serverInfo=getServer().toString();
        String arrayOfDevs="Array[";
        for (Dev dev:getEmpls())
        {
            arrayOfDevs=arrayOfDevs+dev.toString()+"::Dev,";
        }
        arrayOfDevs=arrayOfDevs.substring(0, arrayOfDevs.length()-1);
        arrayOfDevs=arrayOfDevs+"]";
        return "ROW("+projectInfo+","+directorInfo+","+serverInfo+","+arrayOfDevs+","+getId()+")";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;

/**
 *
 * @author ArRMindubaev
 */
@JsonTypeName("director")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "fullName")
public class Director extends Human{
    
    @JsonManagedReference(value = "director-project")
    private List<Project> projects;
    private Group ManagedGroup;
    
    public boolean addDevToProject(Dev dev,Project project){
        if (getProjects().contains(project))
        {
            getGroup().addDev(dev);
            return true;
        }
        return false;
    }

    public Group getManagedGroup() {
        return ManagedGroup;
    }

    public void setManagedGroup(Group ManagedGroup) {
        this.ManagedGroup = ManagedGroup;
    }
    
    /**
     * @return the projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    
}

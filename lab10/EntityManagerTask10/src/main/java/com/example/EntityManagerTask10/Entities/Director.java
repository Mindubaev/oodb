/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EntityManagerTask10.Entities;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author ArRMindubaev
 */
@Entity
public class Director extends Human{
    
    @OneToOne
    private Project project;

    public Director(Project  project ,String fullName, Date birthDate, int salary) {
        super(fullName, birthDate, salary);
        this.project=project;
    }

    public Director() {
        super();
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
}

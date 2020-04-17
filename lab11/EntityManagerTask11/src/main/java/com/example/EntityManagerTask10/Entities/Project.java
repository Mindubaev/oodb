/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EntityManagerTask10.Entities;

import javax.persistence.*;

/**
 *
 * @author ArRMindubaev
 */
@Entity
public class Project {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private TechQuest techQuest;
    @Column
    private int budget;
    
    public Project() {
    }

    public Project(Long id, TechQuest techQuest) {
        this.id = id;
        this.techQuest = techQuest;
        this.budget = budget;
    }
    
    /**
     * @return the techQuest
     */
    public TechQuest getTechQuest() {
        return techQuest;
    }

    /**
     * @param techQuest the techQuest to set
     */
    public void setTechQuest(TechQuest techQuest) {
        this.techQuest = techQuest;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
    
    @Override
    public String toString(){
        String info="";
        if (getTechQuest()!=null)
            info=getTechQuest().toString();
        return "ROW("+getBudget()+","+info+")";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}

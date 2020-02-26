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

/**
 *
 * @author ArRMindubaev
 */
@Entity(name = "Project")
public class Project {
     
    @Id
    private Long id;
    @Column(name = "techQuest")
    @OneToOne(rightSide = TechQuest.class)
    private TechQuest techQuest;
    @Column(name = "budget")
    private int budget;
    @Column(name = "team")
    @ManyToOne(rightClass = Team.class)
    private Team team;
    
    public Project() {
    }

    public Project(Long id, TechQuest techQuest, int budget, Team team) {
        this.id = id;
        this.techQuest = techQuest;
        this.budget = budget;
        this.team = team;
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

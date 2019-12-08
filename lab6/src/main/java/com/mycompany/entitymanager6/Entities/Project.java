/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanager6.Entities;

import com.mycompany.entitymanager6.Annotations.Column;
import com.mycompany.entitymanager6.Annotations.Entity;
import com.mycompany.entitymanager6.Annotations.ManyToOne;

/**
 *
 * @author ArRMindubaev
 */
@Entity(name = "Project")
public class Project {
        
    @Column(name = "techQuest")
    private TechQuest techQuest;
    @Column(name = "budget")
    private int budget;
    @Column(name = "group")
    @ManyToOne(rightClass = Group.class)
    private Group group;
    
    public Project() {
    }

    public Project(TechQuest techQuest, int budget, Group group) {
        this.techQuest = techQuest;
        this.budget = budget;
        this.group = group;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    
}

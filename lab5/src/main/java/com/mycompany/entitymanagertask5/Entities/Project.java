/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask5.Entities;

/**
 *
 * @author ArRMindubaev
 */
public class Project {
        
    private TechQuest techQuest;
    private int budget;
    
    public Project() {
    }

    public Project(TechQuest techQuest,int budget) {
        this.techQuest = techQuest;
        this.budget=budget;
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
    
}

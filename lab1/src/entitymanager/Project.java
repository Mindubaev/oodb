/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitymanager;

/**
 *
 * @author ArRMindubaev
 */
public class Project {

        
    private TechQuest techQuest;
    private Group group;
    
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

    /**
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(Group group) {
        this.group = group;
    }
    
    public Project() {
    }

    public Project(TechQuest techQuest, Group group) {
        this.techQuest = techQuest;
        this.group = group;
    }
    
    
    
}

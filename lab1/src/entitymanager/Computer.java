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
public class Computer {
    
    private Human owner;
    private String description;

    public Computer() {
    }

    /**
     * @return the owner
     */
    public Human getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Human owner) {
        this.owner = owner;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}

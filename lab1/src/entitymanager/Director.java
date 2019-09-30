/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitymanager;

import java.util.List;

/**
 *
 * @author ArRMindubaev
 */
public class Director extends Human{
    
    private Group group;
    private List<Project> projects;
    
    public boolean addDevToProject(Dev dev,Project project){
        if (getProjects().contains(project))
        {
            getGroup().addDev(dev);
            return true;
        }
        return false;
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

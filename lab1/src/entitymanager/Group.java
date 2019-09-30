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
public class Group {

    private List<Human> empls;
    private List<Server> servers;
    
    /**
     * @return the empls
     */
    public List<Human> getEmpls() {
        return empls;
    }

    /**
     * @param empls the empls to set
     */
    public void setEmpls(List<Human> empls) {
        this.empls = empls;
    }

    /**
     * @return the servers
     */
    public List<Server> getServers() {
        return servers;
    }

    /**
     * @param servers the servers to set
     */
    public void setServers(List<Server> servers) {
        this.servers = servers;
    }
    
    public void addDev(Dev dev){
        empls.add(dev);
    } 
    
}

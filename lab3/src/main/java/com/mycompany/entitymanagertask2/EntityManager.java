/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ArRMindubaev
 */
public class EntityManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Director director=new Director();
        Dev dev1=new Dev();
        Dev dev2=new Dev();
        Computer pc1=new Computer(dev1, "description for first pc");
        Computer pc2=new Computer(dev2, "description for second pc");
        dev1.setComputer(pc1);
        dev2.setComputer(pc2);
        Project project=new Project();
        dev1.setProject(project);
        dev2.setProject(project);
        director.setProjects(Arrays.asList(project));
        Group group=new Group();
        Server server=new Server("server for some project", Arrays.asList(project), Arrays.asList(group));
        group.id=1;
        group.setProject(project);
        group.setServer(server);
        group.setEmpls(Arrays.asList(dev1,dev2));
        group.setDirector(director);
        dev1.setGroup(group);
        dev1.setBirthDate("11.12.97");
        dev1.setFullName("Иван Иванов");
        dev1.setSalary(50000);
        dev2.setGroup(group);
        dev2.setBirthDate("22.11.27");
        dev2.setFullName("Василий Тёркин");
        dev2.setSalary(70000);
        director.setGroup(group);
        director.setBirthDate("21.07.67");
        director.setFullName("Тот, чьё имя нельзя называть");
        director.setSalary(1000000);
        director.setManagedGroup(group);
        TechQuest techQuest=new TechQuest( project, "Техзадание для чего-то");
        project.id=2;
        project.setDirector(director);
        project.setGroup(group);
        project.setServer(server);
        project.setTechQuest(techQuest);
        XmlMapper mapper=new XmlMapper();
        try {
            String xml=mapper.writeValueAsString(group);
            System.out.println("");
        } catch (JsonProcessingException ex) {
            Logger.getLogger(EntityManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

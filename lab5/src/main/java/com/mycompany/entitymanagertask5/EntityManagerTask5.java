/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask5;

import com.mycompany.entitymanagertask5.Config.SpringConfig;
import com.mycompany.entitymanagertask5.DAO.GroupService;
import com.mycompany.entitymanagertask5.DAO.PostgresqlServiceImpl;
import com.mycompany.entitymanagertask5.Entities.Computer;
import com.mycompany.entitymanagertask5.Entities.Dev;
import com.mycompany.entitymanagertask5.Entities.Director;
import com.mycompany.entitymanagertask5.Entities.Group;
import com.mycompany.entitymanagertask5.Entities.Project;
import com.mycompany.entitymanagertask5.Entities.Server;
import com.mycompany.entitymanagertask5.Entities.TechQuest;
import java.util.Arrays;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Artur
 */
public class EntityManagerTask5 {

    public static ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
    
    public static void main(String[] args) {
        GroupService groupService=new PostgresqlServiceImpl();
        Group newGroup=new Group(new Director("Андрей Балконский", "1800-02-15", 157000, new Computer("super computer")), 
                Arrays.asList(new Dev(new Computer("desctop computer"),"Пьер Безухов", "1802-08-19", 87000),new Dev(new Computer("laptop"),"Георгий Печорин", "1780-08-19", 97000)), 
                new Server("domain"), 
                new Project(new TechQuest("cyberwar"), 1000000));
        groupService.save(newGroup);
        Group groupFromBD=groupService.findById(newGroup.getId());
        groupFromBD.setServer(new Server("Updated Server"));
        groupService.update(groupFromBD);
        groupService.deleteById(groupFromBD.getId());
    }
    
}

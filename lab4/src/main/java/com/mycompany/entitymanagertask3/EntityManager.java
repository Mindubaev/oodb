/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask3;

import com.mycompany.entitymanagertask3.Entities.TechQuest;
import com.mycompany.entitymanagertask3.Entities.Server;
import com.mycompany.entitymanagertask3.Entities.Project;
import com.mycompany.entitymanagertask3.Entities.Group;
import com.mycompany.entitymanagertask3.Entities.Director;
import com.mycompany.entitymanagertask3.Entities.Dev;
import com.mycompany.entitymanagertask3.Entities.Computer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mycompany.entitymanagertask3.Config.SpringConfig;
import com.mycompany.entitymanagertask3.Converters.JacksonJsonParserService;
import com.mycompany.entitymanagertask3.Converters.JacksonXmlParserService;
import com.mycompany.entitymanagertask3.DAO.GroupService;
import com.mycompany.entitymanagertask3.DAO.PostgresqlServiceImpl;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author ArRMindubaev
 */
public class EntityManager {

    public static ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
    private static GroupService groupService=new PostgresqlServiceImpl();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            File file=new File("src/TestJsonFile.txt");
            JacksonJsonParserService parserService=new JacksonJsonParserService();
            List<Group> groups=parserService.fromFile(file);
            groupService.saveToDB(groups);
            System.out.println("");
            List<Group> gs=groupService.loadFromDB();
        } catch (IOException ex) {
            Logger.getLogger(EntityManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void testJsonService() {
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
        
        Director director2=new Director();
        Dev dev21=new Dev();
        Dev dev22=new Dev();
        Computer pc21=new Computer(dev21, "PC on Mac os");
        Computer pc22=new Computer(dev22, "PC on Windows");
        dev21.setComputer(pc21);
        dev22.setComputer(pc22);
        Project project2=new Project();
        dev21.setProject(project2);
        dev22.setProject(project2);
        director2.setProjects(Arrays.asList(project2));
        Group group2=new Group();
        Server server2=new Server("server for message handling", Arrays.asList(project2), Arrays.asList(group2));
        group2.id=3;
        group2.setProject(project2);
        group2.setServer(server2);
        group2.setEmpls(Arrays.asList(dev21,dev22));
        group2.setDirector(director2);
        dev21.setGroup(group2);
        dev21.setBirthDate("15.10.96");
        dev21.setFullName("Григорий Печорин");
        dev21.setSalary(55000);
        dev22.setGroup(group2);
        dev22.setBirthDate("27.05.12");
        dev22.setFullName("Евгений Онегин");
        dev22.setSalary(77000);
        director2.setGroup(group2);
        director2.setBirthDate("14.06.34");
        director2.setFullName("Родион раскольников");
        director2.setSalary(1030000);
        director2.setManagedGroup(group2);
        TechQuest techQuest2=new TechQuest( project2, "Техзадание веб сервиса");
        project2.id=4;
        project2.setDirector(director2);
        project2.setGroup(group2);
        project2.setServer(server2);
        project2.setTechQuest(techQuest2);
        
        Director director3=new Director();
        Dev dev31=new Dev();
        Computer pc31=new Computer(dev31, "PC on Lunex");
        dev31.setComputer(pc31);
        Project project3=new Project();
        dev31.setProject(project3);
        director3.setProjects(Arrays.asList(project3));
        Group group3=new Group();
        Server server3=new Server("server for testing", Arrays.asList(project3), Arrays.asList(group3));
        group3.id=5;
        group3.setProject(project2);
        group3.setServer(server2);
        group3.setEmpls(Arrays.asList(dev31));
        group3.setDirector(director3);
        dev31.setGroup(group3);
        dev31.setBirthDate("09.10.77");
        dev31.setFullName("Андрей Балконский");
        dev31.setSalary(59000);
        director3.setGroup(group3);
        director3.setBirthDate("04.09.43");
        director3.setFullName("Пьер Безухов");
        director3.setSalary(1100000);
        director3.setManagedGroup(group3);
        TechQuest techQuest3=new TechQuest( project3, "Техзадание для системы безопасности");
        project3.id=6;
        project3.setDirector(director3);
        project3.setGroup(group3);
        project3.setServer(server3);
        project3.setTechQuest(techQuest3);
        
        List<Group> groups=Arrays.asList(group,group2,group3);
        
//        String json;
        try {
//            json = new ObjectMapper().writeValueAsString(groups);
//            System.out.println(json);
//            List<Group> groupsRes=new ObjectMapper().readValue(json,new TypeReference<List<Group>>() {});
            File file=new File("src/TestJsonFile.txt");
            if (!file.exists())
                file.createNewFile();
            JacksonJsonParserService service=new JacksonJsonParserService();
            service.toJson(groups, file);
            List<Group> result1=service.fromFile(file);
            List<Group> result2=ListFilterService.filterBy(result1, (t) -> {
                if (t.getEmpls().size()>=2)
                    return true;
                else
                    return false;
            });
            List<Group> result3=ListFilterService.orderBy(groups, new OrderByEmplNumIncrComparator());
            System.out.println("");
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage());
        }
    }
    
    public static void testXmlService() {
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
        
        Director director2=new Director();
        Dev dev21=new Dev();
        Dev dev22=new Dev();
        Computer pc21=new Computer(dev21, "PC on Mac os");
        Computer pc22=new Computer(dev22, "PC on Windows");
        dev21.setComputer(pc21);
        dev22.setComputer(pc22);
        Project project2=new Project();
        dev21.setProject(project2);
        dev22.setProject(project2);
        director2.setProjects(Arrays.asList(project2));
        Group group2=new Group();
        Server server2=new Server("server for message handling", Arrays.asList(project2), Arrays.asList(group2));
        group2.id=3;
        group2.setProject(project2);
        group2.setServer(server2);
        group2.setEmpls(Arrays.asList(dev21,dev22));
        group2.setDirector(director2);
        dev21.setGroup(group2);
        dev21.setBirthDate("15.10.96");
        dev21.setFullName("Григорий Печорин");
        dev21.setSalary(55000);
        dev22.setGroup(group2);
        dev22.setBirthDate("27.05.12");
        dev22.setFullName("Евгений Онегин");
        dev22.setSalary(77000);
        director2.setGroup(group2);
        director2.setBirthDate("14.06.34");
        director2.setFullName("Родион раскольников");
        director2.setSalary(1030000);
        director2.setManagedGroup(group2);
        TechQuest techQuest2=new TechQuest( project2, "Техзадание веб сервиса");
        project2.id=4;
        project2.setDirector(director2);
        project2.setGroup(group2);
        project2.setServer(server2);
        project2.setTechQuest(techQuest2);
        
        Director director3=new Director();
        Dev dev31=new Dev();
        Computer pc31=new Computer(dev31, "PC on Lunex");
        dev31.setComputer(pc31);
        Project project3=new Project();
        dev31.setProject(project3);
        director3.setProjects(Arrays.asList(project3));
        Group group3=new Group();
        Server server3=new Server("server for testing", Arrays.asList(project3), Arrays.asList(group3));
        group3.id=5;
        group3.setProject(project2);
        group3.setServer(server2);
        group3.setEmpls(Arrays.asList(dev31));
        group3.setDirector(director3);
        dev31.setGroup(group3);
        dev31.setBirthDate("09.10.77");
        dev31.setFullName("Андрей Балконский");
        dev31.setSalary(59000);
        director3.setGroup(group3);
        director3.setBirthDate("04.09.43");
        director3.setFullName("Пьер Безухов");
        director3.setSalary(1100000);
        director3.setManagedGroup(group3);
        TechQuest techQuest3=new TechQuest( project3, "Техзадание для системы безопасности");
        project3.id=6;
        project3.setDirector(director3);
        project3.setGroup(group3);
        project3.setServer(server3);
        project3.setTechQuest(techQuest3);        
        List<Group> groups=Arrays.asList(group,group2,group3);
        try {
            File file=new File("src/TestXMLFile.txt");
            if (!file.exists())
                file.createNewFile();
            JacksonXmlParserService service=new JacksonXmlParserService();
            service.toJson(groups, file);
            List<Group> result1=service.fromFile(file);
            List<Group> result2=ListFilterService.filterBy(result1, (t) -> {
                if (t.getEmpls().size()>=2)
                    return true;
                else
                    return false;
            });
            List<Group> result3=ListFilterService.orderBy(groups, new OrderByEmplNumIncrComparator());
            System.out.println("");
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage());
        }
    }
    
}

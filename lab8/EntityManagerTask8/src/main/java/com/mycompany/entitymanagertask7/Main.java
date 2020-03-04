/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask7;

import com.mycompany.entitymanagertask7.DAO.DBScanner;
import com.mycompany.entitymanagertask7.DAO.EntityManager;
import com.mycompany.entitymanagertask7.DAO.EntityManagerFactory;
import com.mycompany.entitymanagertask7.DAO.EntityManagerPostgresql;
import com.mycompany.entitymanagertask7.Entities.Computer;
import com.mycompany.entitymanagertask7.Entities.Dev;
import com.mycompany.entitymanagertask7.Entities.Director;
import com.mycompany.entitymanagertask7.Entities.Team;
import com.mycompany.entitymanagertask7.MetaClass.EntityMeta;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Artur
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchMethodException, SQLException, ClassNotFoundException, Exception {
        Properties props=new Properties();
        props.setProperty("url", "jdbc:postgresql://localhost:5432/postgres2");
        props.setProperty("username", "postgres");
        props.setProperty("password", "root");
        props.setProperty("driverName", "org.postgresql.Driver");
        String classPath="com.mycompany.entitymanagertask7.Entities";
        EntityManagerFactory factory=new EntityManagerFactory(props, classPath);
        EntityManager emp=factory.create();
        Director newDirector=new Director("New director", "1987-02-13", 200000);
        Dev newDev=new Dev(new Computer("Description for computer!!"), "Artur", "1999-02-01", 100000);//case with only new entities
        Dev newDev2=new Dev(new Computer("Description for Aidar!!"), "Aidar", "1999-01-01", 100000);//case with only new entities
        Team newTeam=new Team();
        List<Dev> list=new ArrayList<>();
        list.add(newDev);
        list.add(newDev2);
        newTeam.setEmpls(list);
        newTeam.setDirector(newDirector);
        Team team=emp.find(Team.class, 1);
        Dev d=emp.find(Dev.class, 1);
        emp.persist(newTeam);
        Dev dev=emp.find(Dev.class,1);
        dev=emp.refresh(dev);
        team=emp.refresh(team);
        team.getEmpls().remove(dev);
        team.setServers(null);;
        team=emp.merge(team);
        dev.setComputer(null);
        dev.setSalary(666666666);
        dev.setFullName("Name was changed");
        dev.setComputer(new Computer("Computer for merge test"));
        dev=emp.merge(dev);
    }
    
    public static void writeToFile(String str){
        File file =new File("src/metaClasses.txt");
        if (!file.exists())
            try {
                file.createNewFile();
        } catch (IOException ex) {
                System.err.println("Can not create file cause:"+ex );
        }
        try(FileWriter writer=new FileWriter(file,false)){
            writer.write(str);
        } catch (IOException ex) {
            System.err.println("Can not write file cause:"+ex );
        }
    }
    
}

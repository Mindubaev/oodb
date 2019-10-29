/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask3.DAO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mycompany.entitymanagertask3.Entities.Group;
import java.util.List;

/**
 *
 * @author Artur
 */
public interface GroupService {
    
    void saveToDB(List<Group> groups) throws JsonProcessingException;
    List<Group> loadFromDB() throws JsonProcessingException;
    
}

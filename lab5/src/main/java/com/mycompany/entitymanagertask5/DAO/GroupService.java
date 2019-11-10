/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask5.DAO;

import com.mycompany.entitymanagertask5.Entities.Group;
import java.util.List;

/**
 *
 * @author Artur
 */
public interface GroupService {
    
    void save(Group group);
    void deleteById(Long id);
    void update(Group group);
    Group findById(Long id) ;
    
}

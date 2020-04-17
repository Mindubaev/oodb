/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EntityManagerTask10.DAO.Team;

import com.example.EntityManagerTask10.Entities.Team;
import java.util.List;

/**
 *
 * @author Artur
 */
public interface TeamService {
    
    Team save(Team team);
    void delete(Team team);
    void deleteById(Long id);
    Team findById(Long id);
    List<Team> findAll();
    
}

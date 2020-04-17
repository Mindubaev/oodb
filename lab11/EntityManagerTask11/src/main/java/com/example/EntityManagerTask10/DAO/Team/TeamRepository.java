/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EntityManagerTask10.DAO.Team;

import com.example.EntityManagerTask10.Entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Artur
 */
public interface TeamRepository extends JpaRepository<Team, Long>{
    
}

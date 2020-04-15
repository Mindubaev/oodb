/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EntityManagerTask10.DAO.Dev;

import com.example.EntityManagerTask10.Entities.Dev;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Artur
 */
public interface DevService {
    
    Dev save(Dev dev);
    void delete(Dev dev);
    void deleteById(Long id);
    Dev findById(Long id);
    List<Dev> findAll();
    List<Dev> findBySalary(int salary); 
    List<Dev> searchByFullName(String fullName); 
    
}

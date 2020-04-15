/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EntityManagerTask10.DAO.Dev;

import com.example.EntityManagerTask10.Entities.Dev;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Artur
 */
public interface DevRepository extends JpaRepository<Dev, Long>{
    
    @Query(nativeQuery = false,value = "select d from Dev d where d.salary=?1")
    Optional<List<Dev>> findBySalary(int salary); 
    @Query(nativeQuery = false,value = "select d from Dev d where d.fullName LIKE concat(concat('%', ?1), '%')")
    Optional<List<Dev>> searchByFullName(String fullName); 
    
}

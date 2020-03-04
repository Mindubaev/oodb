/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask7.DAO;

/**
 *
 * @author Artur
 */
public interface EntityManager {
    
    void persist(Object obj);
    <T> T merge(T obj);
    void remove(Object obj);
    <T> T find(Class<T> clazz,Object obj);
    <T> T refresh(T obj);
    
}

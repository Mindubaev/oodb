/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask2;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Artur
 */
public interface ParserService {
    
    public List<Group> fromStr(String json) throws JsonProcessingException;
    
    public List<Group> fromFile(File file) throws JsonProcessingException,IOException;
    
    public String toJson(List<Group> groups) throws JsonProcessingException;
    
    public boolean toJson(List<Group> groups,File file) throws JsonProcessingException;
    
}

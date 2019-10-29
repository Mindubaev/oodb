/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask3.Converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.mycompany.entitymanagertask3.Entities.Group;
import com.mycompany.entitymanagertask3.Converters.ParserService;
import com.mycompany.entitymanagertask3.Entities.Group;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Artur
 */
public class JacksonJsonParserService implements ParserService{

    @Override
    public List<Group> fromStr(String json) throws JsonProcessingException{
        
        return new ObjectMapper().readValue(json,new TypeReference<List<Group>>() {});
    }

    @Override
    public List<Group> fromFile(File file) throws JsonProcessingException,IOException{
        return new ObjectMapper().readValue(file, new TypeReference<List<Group>>() {});
    }

    @Override
    public String toJson(List<Group> groups) throws JsonProcessingException{
        return new ObjectMapper().writeValueAsString(groups);
    }

    @Override
    public boolean toJson(List<Group> groups, File file) throws JsonProcessingException{
        try{
            new ObjectMapper().writeValue(file, groups);
            return true;
        }catch(IOException ex){
            return false;
        }
    }
    
}

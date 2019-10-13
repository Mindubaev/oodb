/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Artur
 */
public class JacksonXmlParserServer implements ParserService{

    @Override
    public List<Group> fromStr(String json) throws JsonProcessingException{
        
        return new XmlMapper().readValue(json,new TypeReference<List<Group>>() {});
    }

    @Override
    public List<Group> fromFile(File file) throws JsonProcessingException,IOException{
        return new XmlMapper().readValue(file, new TypeReference<List<Group>>() {});
    }

    @Override
    public String toJson(List<Group> groups) throws JsonProcessingException{
        return new XmlMapper().writeValueAsString(groups);
    }

    @Override
    public boolean toJson(List<Group> groups, File file) throws JsonProcessingException{
        try{
            new XmlMapper().writeValue(file, groups);
            return true;
        }catch(IOException ex){
            return false;
        }
    }
    
}

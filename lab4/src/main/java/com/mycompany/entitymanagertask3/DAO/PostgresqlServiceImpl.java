/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask3.DAO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mycompany.entitymanagertask3.Converters.JacksonJsonParserService;
import com.mycompany.entitymanagertask3.Entities.Group;
import com.mycompany.entitymanagertask3.EntityManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.graalvm.compiler.nodes.extended.RawStoreNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 *
 * @author Artur
 */
public class PostgresqlServiceImpl implements GroupService{
    
    public JdbcTemplate jdbcTemplate=EntityManager.context.getBean("jdbcTemplate",JdbcTemplate.class);

    public PostgresqlServiceImpl() {
    }

    public PostgresqlServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveToDB(List<Group> groups) throws JsonProcessingException{
        JacksonJsonParserService parserService=new JacksonJsonParserService();
        String json=parserService.toJson(groups);
        jdbcTemplate.update("insert into jsonTest (jsonGroups,jsonbGroups) values (cast(? as json),cast(? as json))", json,json);
        
    }

    @Override
    public List<Group> loadFromDB() throws JsonProcessingException{
        List<String> rows=jdbcTemplate.query("select jsonbGroups from jsonTest",new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int arg1) throws SQLException {
                return rs.getString("jsonbGroups");              
            }
        });
        JacksonJsonParserService parserService=new JacksonJsonParserService();
        if (rows.size()>0)
            return parserService.fromStr(rows.get(0));
        else
            return new ArrayList<Group>();
    }
    
}

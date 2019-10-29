/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask3.Config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Artur
 */
@Configuration
@ComponentScan("com.mycompany.entitymanagertask3")
public class SpringConfig {
    
    @Bean
    public DataSource dataSource(){//sql scheme |Create table jsonTest (id serial,jsonGroups text,jsonbGroups text);
        DriverManagerDataSource dataSource=new DriverManagerDataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entitymanagertask5.DAO;

import com.mycompany.entitymanagertask5.Entities.Computer;
import com.mycompany.entitymanagertask5.Entities.Dev;
import com.mycompany.entitymanagertask5.Entities.Director;
import com.mycompany.entitymanagertask5.Entities.Group;
import com.mycompany.entitymanagertask5.Entities.Project;
import com.mycompany.entitymanagertask5.Entities.Server;
import com.mycompany.entitymanagertask5.Entities.TechQuest;
import com.mycompany.entitymanagertask5.EntityManagerTask5;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.postgresql.util.PGobject;
import org.postgresql.util.PGtokenizer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author Artur
 */
public class PostgresqlServiceImpl implements GroupService{
    
    public JdbcTemplate jdbcTemplate=EntityManagerTask5.context.getBean("jdbcTemplate",JdbcTemplate.class);

    public PostgresqlServiceImpl() {
    }

    @Override
    public void save(Group group) {
        KeyHolder keyHolder=new GeneratedKeyHolder();
        String sql="insert into company (devGroup) values ("+group.toString()+")";
        PreparedStatementCreator psc=new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            }
        };
        jdbcTemplate.update(psc, keyHolder);
//        List<Long> result=jdbcTemplate.query("select id from Company where devGroup=("+group.toString()+")", new RowMapper<Long>() {
//            @Override
//            public Long mapRow(ResultSet rs, int arg1) throws SQLException {
//                return rs.getLong("id");
//            }
//        });
        group.setId(Long.parseLong(keyHolder.getKeyList().get(0).get("id").toString()));
    }

    @Override
    public void update(Group group) {
        jdbcTemplate.update("update company set devGroup=("+group.toString()+") where id="+group.getId());
    }

    @Override
    public Group findById(Long id) {
        List<Group> groups=jdbcTemplate.query("select (devGroup) from company where id="+id, new RowMapper<Group>() {
            @Override
            public Group mapRow(ResultSet rs, int arg1) throws SQLException {
                PGobject pGobject=(PGobject)(rs.getObject("devGroup"));
                String GroupStr=pGobject.getValue();
                if (GroupStr!=null && GroupStr.length()>0)
                {
                    GroupStr=GroupStr;
                    GroupStr=GroupStr.substring(1, GroupStr.length()-1);
                    PGtokenizer groupTokenizer=new PGtokenizer(GroupStr, ',');
                    Group group=null;
                    if (groupTokenizer.getSize()==5)
                    {
                        String projectInfo=groupTokenizer.getToken(0).replace("\"","");
                        String directorStr=groupTokenizer.getToken(1).replace("\"","");
                        String serverStr=groupTokenizer.getToken(2).replace("\"","");
                        String devArrayStr=groupTokenizer.getToken(3);
                        
                        projectInfo=projectInfo.substring(1, projectInfo.length()-1);
                        PGtokenizer projectTokinazer=new PGtokenizer(projectInfo, ',');
                        Project project=null;
                        if (projectTokinazer.getSize()==2)
                        {
                            String budget=projectTokinazer.getToken(0);
                            String techRequestStr=projectTokinazer.getToken(1);
                            techRequestStr=techRequestStr.substring(1, techRequestStr.length()-1);
                            PGtokenizer techRequestTokenizer=new PGtokenizer(techRequestStr,',');
                            TechQuest techQuest=null;
                            if (techRequestTokenizer.getSize()==1)
                            {
                                techRequestStr=techRequestTokenizer.getToken(0);
                                techQuest=new TechQuest(techRequestStr);
                            }
                            project=new Project(techQuest, Integer.parseInt(budget));
                        }
                        
                        directorStr=directorStr.substring(1,directorStr.length()-1);
                        PGtokenizer directorTokinazer=new PGtokenizer(directorStr, ',');
                        Director director=null;
                        if (directorTokinazer.getSize()==4)
                        {
                            String fullname=directorTokinazer.getToken(0);
                            String salary=directorTokinazer.getToken(1);
                            String birthDate=directorTokinazer.getToken(2);
                            String computerStr=directorTokinazer.getToken(3);
                            computerStr=computerStr.substring(1, computerStr.length()-1);
                            PGtokenizer computerTokenizer=new PGtokenizer(computerStr, ',');
                            Computer computer=null;
                            if (computerTokenizer.getSize()==1)
                            {
                                computer=new Computer(computerTokenizer.getToken(0));
                            }
                            director=new Director(fullname, birthDate, Integer.parseInt(salary), computer);
                        }
                        
                        serverStr=serverStr.substring(1, serverStr.length()-1);
                        PGtokenizer serverTokinazer=new PGtokenizer(serverStr,',');
                        Server server=null;
                        if (serverTokinazer.getSize()==1)
                        {
                            server=new Server(serverTokinazer.getToken(0));
                        }
                        
                        List<Dev> devs=deserializeDevArray(devArrayStr);
                        group=new Group(id,director, devs, server, project);
                    }
                    return group;  
                }
                else
                    return null;
            }
            
            private List<Dev> deserializeDevArray(String devArrayStr){
                List<Dev> devs=new ArrayList<>();
                devArrayStr=devArrayStr.substring(3, devArrayStr.length()-3).replace("\\", "").replace("\"\"", "\"");
                PGtokenizer devArrTokinazer=new PGtokenizer(devArrayStr, ',');
                for (int i=0;i<devArrTokinazer.getSize();i++)
                {
                    String devStr=devArrTokinazer.getToken(i).replace("\"", "");
                    devStr=devStr.substring(1,devStr.length()-1);
                        PGtokenizer devTokinazer=new PGtokenizer(devStr, ',');
                        Dev dev=null;
                        if (devTokinazer.getSize()==4)
                        {
                            String fullname=devTokinazer.getToken(0);
                            String salary=devTokinazer.getToken(1);
                            String birthDate=devTokinazer.getToken(2);
                            String computerStr=devTokinazer.getToken(3);
                            computerStr=computerStr.substring(1, computerStr.length()-1);
                            PGtokenizer computerTokenizer=new PGtokenizer(computerStr, ',');
                            Computer computer=null;
                            if (computerTokenizer.getSize()==1)
                            {
                                computer=new Computer(computerTokenizer.getToken(0));
                            }
                            dev=new Dev(computer,fullname, birthDate, Integer.parseInt(salary));
                            devs.add(dev);
                        }
                }
                return devs;
            }
            
        });
        if (groups==null || groups.size()==0)
            return null;
        return groups.get(0);
    }
    
    @Override
    public void deleteById(Long id){
        jdbcTemplate.update("delete from company where id="+id);
    }
    
    
}

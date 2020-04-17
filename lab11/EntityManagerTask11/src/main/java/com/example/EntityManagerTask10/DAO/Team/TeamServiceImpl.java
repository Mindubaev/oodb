/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EntityManagerTask10.DAO.Team;

import com.example.EntityManagerTask10.Entities.Team;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Artur
 */
@Transactional
@Service("teamService")
@Repository
public class TeamServiceImpl implements TeamService{
    
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void delete(Team team) {
        teamRepository.delete(team);
    }

    @Override
    public void deleteById(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public Team findById(Long id) {
        Optional<Team> op=teamRepository.findById(id);
        if (op.isPresent())
            return op.get();
        return null;
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }
    
}

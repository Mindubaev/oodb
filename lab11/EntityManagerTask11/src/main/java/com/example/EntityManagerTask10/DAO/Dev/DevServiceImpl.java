/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EntityManagerTask10.DAO.Dev;

import com.example.EntityManagerTask10.DAO.Team.TeamService;
import com.example.EntityManagerTask10.Entities.Dev;
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
@Repository
@Service("devService")
@Transactional
public class DevServiceImpl implements DevService{

    @Autowired
    public DevRepository devRepository;
    @Autowired
    public TeamService teamService;
    
    @Override
    public Dev save(Dev dev) {
        return devRepository.save(dev);
    }

    @Override
    public void delete(Dev dev) {
        devRepository.delete(dev);
    }

    @Override
    public void deleteById(Long id) {
        devRepository.deleteById(id);
    }

    @Override
    public Dev findById(Long id) {
        Optional<Dev> op=devRepository.findById(id);
        if (op.isPresent())
            return op.get();
        return null;
    }
    
    @Override
    public List<Dev> findAll() {
        return devRepository.findAll();
    }

    @Override
    public List<Dev> findBySalary(int salary) {
        return checkList(devRepository.findBySalary(salary));
    }

    @Override
    public List<Dev> searchByFullName(String fullName) {
        return checkList(devRepository.searchByFullName(fullName));
    }
    
    private List<Dev> checkList(Optional<List<Dev>> optional) {
    if (optional.isPresent()) {
        return optional.get();
    } else {
        return null;
    }
    }

    @Override
    public Dev save(Dev dev, Long teamId) {
        dev=this.save(dev);
        Team team=teamService.findById(teamId);
        if (team!=null){
            team.getEmpls().add(dev);
            team=teamService.save(team);
        }
        return dev;
    }
    
}

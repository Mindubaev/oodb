/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EntityManagerTask10.Controllers;

import com.example.EntityManagerTask10.DAO.Dev.DevService;
import com.example.EntityManagerTask10.DAO.Team.TeamService;
import com.example.EntityManagerTask10.Entities.Dev;
import com.example.EntityManagerTask10.Entities.Team;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Artur
 */
@Controller
public class DevController {
    
    @Autowired
    private DevService devService;
    @Autowired
    private TeamService teamService;
    
    @GetMapping("")
    public String getHomePage(){
        return "home";
    }
    
    @GetMapping("/devs")
    public String getDevs(Model model,@RequestParam(name = "fullName",required = false) String fullName){
        List<Dev> devs=null;
        if (fullName==null)
            devs=devService.findAll();
        else
            devs=devService.searchByFullName(fullName);
        model.addAttribute("devs",devs);
        return "DevList";
    }
    
    @GetMapping("/dev")
    public String getNewDevForm(Model model){
        model.addAttribute("dev",new Dev());
        return "DevPage";
    }
    
    @PostMapping("/dev")
    public String postDev(Dev dev,@RequestParam(name = "teamId",required = true) Long id){
        devService.save(dev, id);
        return "redirect:/devs";
    }
    
}

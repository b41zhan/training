package com.springdemo.trainingproject.controllers;

import com.springdemo.trainingproject.model.User;
import com.springdemo.trainingproject.services.TaskService;
import com.springdemo.trainingproject.services.UserService;
import com.springdemo.trainingproject.services.UserServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainControl {
    @Autowired
   private final UserServiceIMPL userService;
    @Autowired
   private final TaskService taskService;


   public MainControl(UserServiceIMPL userService, TaskService taskService) {
       this.userService = userService;
       this.taskService = taskService;
   }



    @GetMapping("/main")
    public String showProfilePage(Model model, Principal principal) {
        try {
            String email = principal.getName();
            User user = userService.findByEmail(email);

            model.addAttribute("user", user);
            return "main";
        } catch (Exception e) {
            System.err.println("Error in showProfilePage: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }







}

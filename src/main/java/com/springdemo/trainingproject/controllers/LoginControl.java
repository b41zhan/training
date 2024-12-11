package com.springdemo.trainingproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControl {

    @GetMapping("/login")
    public String login(){
        return "login";
    }


}

package com.springdemo.trainingproject.controllers;

import com.springdemo.trainingproject.dto.UserReg;
import com.springdemo.trainingproject.model.User;
import com.springdemo.trainingproject.services.UserService;
import com.springdemo.trainingproject.services.UserServiceIMPL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterCon {
    private final UserService userService;
    public RegisterCon(UserServiceIMPL userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserReg userReg() {
        return new UserReg();
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") UserReg userDto) {
        userService.save(userDto);
        return "redirect:/register?success";
    }



}

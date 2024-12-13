package com.springdemo.trainingproject.controllers;

import com.springdemo.trainingproject.dto.UserReg;
import com.springdemo.trainingproject.model.User;
import com.springdemo.trainingproject.services.EmailService;
import com.springdemo.trainingproject.services.UserService;
import com.springdemo.trainingproject.services.UserServiceIMPL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterCon {
    private final UserServiceIMPL userService;

    private final EmailService emailService;
    public RegisterCon(UserServiceIMPL userService, EmailService emailService) {

        this.userService = userService;
        this.emailService = emailService;
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
       User user =  userService.save(userDto);
        emailService.sendCode(user.getEmail(), user.getUsername());
        return "redirect:/register?success";
    }



}

package com.springdemo.trainingproject.services;

import com.springdemo.trainingproject.dto.UserReg;
import com.springdemo.trainingproject.model.User;
import com.springdemo.trainingproject.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Date;

@Service
public class UserServiceIMPL implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    public UserServiceIMPL(UserRepo userRepo) {
        super();
        this.userRepo = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }



    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }


    @Override
    public User save(UserReg userRegistrationDto) {
        User user = new User(userRegistrationDto.getUsername(),
                userRegistrationDto.getEmail(),
                passwordEncoder.encode(userRegistrationDto.getPassword()));
        return userRepo.save(user);
    }

    public void save(User user) {
        userRepo.save(user);
    }





    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(username);
        System.out.println(user.getEmail() + user.getPassword());
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}

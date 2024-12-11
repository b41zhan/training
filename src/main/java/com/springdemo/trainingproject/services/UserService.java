package com.springdemo.trainingproject.services;

import com.springdemo.trainingproject.dto.UserReg;
import com.springdemo.trainingproject.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
User save(UserReg user);
}

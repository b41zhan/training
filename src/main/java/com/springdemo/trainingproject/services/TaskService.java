package com.springdemo.trainingproject.services;


import com.springdemo.trainingproject.repositories.TaskRepo;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepo taskRepo;
    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }





}

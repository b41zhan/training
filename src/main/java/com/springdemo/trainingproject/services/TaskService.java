package com.springdemo.trainingproject.services;


import com.springdemo.trainingproject.model.Task;
import com.springdemo.trainingproject.repositories.TaskRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepo taskRepo;
    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Page<Task> findTaskByUserId(Long userId, Pageable pageable) {
        return taskRepo.findByUserId(userId, pageable);
    }

    public void save(Task task) {
        taskRepo.save(task);
    }


    public Page<Task> findByUserIdAndCategoryId(Long userId, String status, Pageable pageable) {
        return taskRepo.findByUserIdAndStatus(userId, status, pageable);}

        public Page<Task> findByUserIdAndTitleContainingAndStatus(Long userId, String title, String status, Pageable pageable) {
            return taskRepo.findByUserIdAndTitleContainingAndStatus(userId, title, status, pageable);
        }

        public Page<Task> findByUserIdAndTitleContaining(Long userId, String title, Pageable pageable) {
            return taskRepo.findByUserIdAndTitleContaining(userId, title, pageable);
        }

        public Page<Task> findByUserIdAndStatus(Long userId, String status, Pageable pageable) {
            return taskRepo.findByUserIdAndStatus(userId, status, pageable);
        }

        public Page<Task> findByUserId(Long userId, Pageable pageable) {
            return taskRepo.findByUserId(userId, pageable);
        }

    public void deleteById(Long id) {
        taskRepo.deleteById(id);
    }
    public Page<Task> findTasksByUserId(Long userId, Pageable pageable) {
        return taskRepo.findByUserId(userId, pageable);
    }


    public List<Task> findTasksByUserId(Long userId) {
        List<Task> tasks = taskRepo.findByUserId(userId);
        System.out.println("Tasks found: " + tasks);
        return tasks;
    }


    public Task findById(Long id) {
        return taskRepo.findById(id).orElse(null); // Используем Optional
    }




}

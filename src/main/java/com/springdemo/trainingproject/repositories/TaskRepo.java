package com.springdemo.trainingproject.repositories;

import com.springdemo.trainingproject.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository <Task, Long> {

    List<Task> findByUserId(Long userId);
    Page<Task> findByUserId(Long userId, Pageable pageable);
    Page<Task> findByUserIdAndTitleContaining(Long userId, String title, Pageable pageable);
    Page<Task> findByUserIdAndTitleContainingAndStatus(Long userId,String title,String status,Pageable pageable);
    Page<Task> findByUserIdAndStatus(Long userId, String status, Pageable pageable);
    Optional<Task> findById(Long id);
    Task save(Task task);
}

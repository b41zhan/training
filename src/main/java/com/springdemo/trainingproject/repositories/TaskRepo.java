package com.springdemo.trainingproject.repositories;

import com.springdemo.trainingproject.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository <Task, Long> {

}

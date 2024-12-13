package com.springdemo.trainingproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="task_tb")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;
    private String photoLink = "/images/default.jpg"; // Значение по умолчанию

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Task() {

    }

    public Task(String title, String description, String status,User user) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.user = user;
    }
    public Task(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }
    public Task(Long id, String title, String description, String status,User user) {
        this.title = title;
        this.id = id;
        this.description = description;
        this.status = status;
        this.user = user;
    }




    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public void setUser(User user) {
        this.user = user;
    }
}

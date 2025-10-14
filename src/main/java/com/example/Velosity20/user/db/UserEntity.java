package com.example.Velosity20.user.db;

import com.example.Velosity20.project.db.ProjectEntity;
import com.example.Velosity20.task.db.TaskEntity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
    private List<ProjectEntity> projectEntities;

    @OneToMany(mappedBy = "executor")
    private List<TaskEntity> taskEntityList;

    public UserEntity() {
    }

    public UserEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ProjectEntity> getProjects() {
        return projectEntities;
    }

    public void setProjects(List<ProjectEntity> projectEntities) {
        this.projectEntities = projectEntities;
    }

    public List<TaskEntity> getTaskList() {
        return taskEntityList;
    }

    public void setTaskList(List<TaskEntity> taskEntityList) {
        this.taskEntityList = taskEntityList;
    }
}

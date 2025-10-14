package com.example.Velosity20.project.db;

import com.example.Velosity20.task.db.TaskEntity;
import com.example.Velosity20.user.db.UserEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Column;

import java.util.List;

@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "project")
    private List<TaskEntity> taskEntityList;

    public ProjectEntity() {
    }

    public ProjectEntity(String name, UserEntity userEntity) {
        this.name = name;
        this.userEntity = userEntity;
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

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<TaskEntity> getTaskList() {
        return taskEntityList;
    }

    public void setTaskList(List<TaskEntity> taskEntityList) {
        this.taskEntityList = taskEntityList;
    }
}

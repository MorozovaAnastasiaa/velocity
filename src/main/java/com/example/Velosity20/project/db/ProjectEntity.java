package com.example.Velosity20.project.db;

import com.example.Velosity20.column.db.ColumnEntity;
import com.example.Velosity20.task.db.TaskEntity;
import com.example.Velosity20.user.db.UserEntity;

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
    private UserEntity user;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<ColumnEntity> columns;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<TaskEntity> tasks;

    public ProjectEntity() {
    }

    public ProjectEntity(String name, UserEntity userEntity) {
        this.name = name;
        this.user = userEntity;
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
        return user;
    }

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
    }

    public List<TaskEntity> getTaskList() {
        return tasks;
    }

    public void setTaskList(List<TaskEntity> taskEntityList) {
        this.tasks = taskEntityList;
    }

    public List<ColumnEntity> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnEntity> columns) {
        this.columns = columns;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }
}

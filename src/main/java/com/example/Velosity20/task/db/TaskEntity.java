package com.example.Velosity20.task.db;

import com.example.Velosity20.project.db.ProjectEntity;
import com.example.Velosity20.user.db.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "deadline")
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private UserEntity executor;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private ProjectEntity project;

    public TaskEntity() {
    }

    public TaskEntity(String name, String description, LocalDate startDate, LocalDate deadline, UserEntity executor, ProjectEntity projectEntity) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.deadline = deadline;
        this.executor = executor;
        this.project = projectEntity;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public UserEntity getExecutor() {
        return executor;
    }

    public void setExecutor(UserEntity executor) {
        this.executor = executor;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity projectEntity) {
        this.project = projectEntity;
    }
}

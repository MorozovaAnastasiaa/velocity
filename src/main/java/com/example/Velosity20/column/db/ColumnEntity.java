package com.example.Velosity20.column.db;

import com.example.Velosity20.project.db.ProjectEntity;
import com.example.Velosity20.task.db.TaskEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "columns")
public class ColumnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private ProjectEntity project;

    @OneToMany(mappedBy = "column", cascade = CascadeType.REMOVE)
    private List<TaskEntity> tasks;

    public ColumnEntity() {
    }

    public ColumnEntity(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }
}

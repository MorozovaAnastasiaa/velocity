package com.example.Velosity20.task.db;

import com.example.Velosity20.project.db.ProjectEntity;
import com.example.Velosity20.project.db.ProjectRepository;
import com.example.Velosity20.task.dto.TaskRequestDto;
import com.example.Velosity20.task.dto.TaskResponseDto;
import com.example.Velosity20.user.db.UserEntity;
import com.example.Velosity20.user.db.UserRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class TaskMapper {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskMapper(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }


    public TaskResponseDto toResponse(TaskEntity task) {
        return new TaskResponseDto(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStartDate(),
                task.getDeadline(),
                task.getExecutor().getId(),
                task.getProject().getId()
        );
    }

    public TaskEntity toEntity(TaskRequestDto requestDto) {
        UserEntity user = userRepository.findById(requestDto.executorId())
                .orElseThrow(() -> new NoSuchElementException());

        ProjectEntity project = projectRepository.findById(requestDto.projectId())
                .orElseThrow(() -> new NoSuchElementException());

        return new TaskEntity(
                requestDto.name(),
                requestDto.description(),
                requestDto.startDate(),
                requestDto.deadline(),
                user,
                project
        );
    }
}

package com.example.Velosity20.task.domain;

import com.example.Velosity20.task.db.TaskEntity;
import com.example.Velosity20.task.db.TaskMapper;
import com.example.Velosity20.task.db.TaskRepository;
import com.example.Velosity20.task.dto.TaskFilterDto;
import com.example.Velosity20.task.dto.TaskRequestDto;
import com.example.Velosity20.task.dto.TaskResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper mapper;

    public TaskService(TaskRepository repository, TaskMapper mapper) {
        this.taskRepository = repository;
        this.mapper = mapper;
    }

    public List<TaskResponseDto> findAllByFilter(TaskFilterDto filterDto) {

        int pageSize = filterDto.pageSize() != null ? filterDto.pageSize() : 100;
        int pageNumber = filterDto.pageNumber() != null ? filterDto.pageNumber() : 0;

        Pageable pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);

        return taskRepository.findAllByFilter(filterDto.executorId(), filterDto.projectId(), pageable).stream()
                .map(mapper::toResponse)
                .toList();
    }

    public TaskResponseDto findById(Long id) {
        return mapper.toResponse(taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException()));
    }

    public TaskResponseDto createTask(TaskRequestDto task) {
        TaskEntity taskToCreate = mapper.toEntity(task);
        return mapper.toResponse(taskRepository.save(taskToCreate));
    }

    public void deleteProject(Long id) {
        if (taskRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Element with id = " + id + " not found");
        }

        taskRepository.deleteById(id);
    }

    public TaskResponseDto updateProject(Long id, TaskRequestDto task) {
        if (taskRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Element with id = " + id + " not found");
        }

        TaskEntity taskToUpdate = mapper.toEntity(task);
        taskToUpdate.setId(id);

        return mapper.toResponse(taskRepository.save(taskToUpdate));
    }

    public List<TaskResponseDto> findTasksByUserId(Long id) {
        return taskRepository.findTasksByUserId(id).stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<TaskResponseDto> findTasksByProjectId(Long id) {
        return taskRepository.findTasksByProjectId(id).stream()
                .map(mapper::toResponse)
                .toList();
    }
}


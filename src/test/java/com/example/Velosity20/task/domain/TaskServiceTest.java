package com.example.Velosity20.task.domain;

import com.example.Velosity20.project.db.ProjectEntity;
import com.example.Velosity20.project.dto.ProjectRequestDto;
import com.example.Velosity20.project.dto.ProjectResponseDto;
import com.example.Velosity20.task.db.TaskEntity;
import com.example.Velosity20.task.db.TaskMapper;
import com.example.Velosity20.task.db.TaskRepository;
import com.example.Velosity20.task.dto.TaskRequestDto;
import com.example.Velosity20.task.dto.TaskResponseDto;
import com.example.Velosity20.user.db.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository repository;

    @Mock
    TaskMapper mapper;

    @InjectMocks
    TaskService service;

    @Test
    void findAllByFilter() {
    }

    @Test
    void findById() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity("nastya", "n@gmail.com", "12345");
        ProjectEntity projectEntity = new ProjectEntity("project1", userEntity);
        TaskEntity taskEntity = new TaskEntity("name", "description", LocalDate.now(),
                LocalDate.now().plusDays(2L), userEntity, projectEntity);

        TaskResponseDto responseDto = new TaskResponseDto(1L, "name", "description", LocalDate.now(),
                LocalDate.now().plusDays(2L), 1L, 1L);

        when(repository.findById(id)).thenReturn(Optional.of(taskEntity));
        when(mapper.toResponse(taskEntity)).thenReturn(responseDto);

        assertEquals(responseDto, service.findById(id));

        verify(mapper).toResponse(taskEntity);
        verify(repository).findById(id);
    }

    @Test
    void createTask() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity("nastya", "n@gmail.com", "12345");
        ProjectEntity projectEntity = new ProjectEntity("project1", userEntity);
        TaskRequestDto requestDto = new TaskRequestDto("name", "description", LocalDate.now(),
                LocalDate.now().plusDays(2L), 1L, 1L);
        TaskEntity taskEntity = new TaskEntity("name", "description", LocalDate.now(),
                LocalDate.now().plusDays(2L), userEntity, projectEntity);
        TaskResponseDto responseDto = new TaskResponseDto(1L, "name", "description", LocalDate.now(),
                LocalDate.now().plusDays(2L), 1L, 1L);

        when(mapper.toEntity(requestDto)).thenReturn(taskEntity);
        when(repository.save(taskEntity)).thenReturn(taskEntity);
        when(mapper.toResponse(taskEntity)).thenReturn(responseDto);

        TaskResponseDto result = service.createTask(requestDto);

        assertEquals(responseDto, result);
        verify(mapper).toEntity(requestDto);
        verify(repository).save(taskEntity);
        verify(mapper).toResponse(taskEntity);
    }
}
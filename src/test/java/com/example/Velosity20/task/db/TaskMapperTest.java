package com.example.Velosity20.task.db;

import com.example.Velosity20.project.db.ProjectEntity;
import com.example.Velosity20.project.db.ProjectRepository;
import com.example.Velosity20.task.dto.TaskRequestDto;
import com.example.Velosity20.task.dto.TaskResponseDto;
import com.example.Velosity20.user.db.UserEntity;
import com.example.Velosity20.user.db.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskMapperTest {

    private static final Long ID = 1L;

    @Mock
    UserRepository userRepository;

    @Mock
    ProjectRepository projectRepository;

    @InjectMocks
    TaskMapper mapper;

    @Test
    void toResponse() {
        UserEntity userEntity = new UserEntity("nastya", "n@gmail.com", "12345");
        userEntity.setId(ID);

        ProjectEntity projectEntity = new ProjectEntity("project1", userEntity);
        projectEntity.setId(ID);

        TaskEntity taskEntity = new TaskEntity("name", "description", LocalDate.now(),
                LocalDate.now().plusDays(2L), userEntity, projectEntity);
        taskEntity.setId(ID);

        TaskResponseDto expectedDto = new TaskResponseDto(ID, "name", "description",
                LocalDate.now(), LocalDate.now().plusDays(2L), ID, ID);

        TaskResponseDto result = mapper.toResponse(taskEntity);

        assertEquals(expectedDto, result);
    }

    @Test
    void toEntity() {
        UserEntity userEntity = new UserEntity("nastya", "n@gmail.com", "12345");
        userEntity.setId(ID);

        ProjectEntity projectEntity = new ProjectEntity("project1", userEntity);
        projectEntity.setId(ID);

        TaskRequestDto requestDto = new TaskRequestDto("name", "description", LocalDate.now(),
                LocalDate.now().plusDays(2L), ID, ID);

        when(userRepository.findById(ID)).thenReturn(Optional.of(userEntity));
        when(projectRepository.findById(ID)).thenReturn(Optional.of(projectEntity));

        TaskEntity result = mapper.toEntity(requestDto);

        assertEquals("name", result.getName());
        assertEquals("description", result.getDescription());
        assertEquals(LocalDate.now(), result.getStartDate());
        assertEquals(LocalDate.now().plusDays(2L), result.getDeadline());
        assertEquals(userEntity, result.getExecutor());
        assertEquals(projectEntity, result.getProject());
        assertNull(result.getId());
    }

    @Test
    void toEntity_ShouldThrowException_WhenUserNotFound() {
        TaskRequestDto requestDto = new TaskRequestDto("name", "description", LocalDate.now(),
                LocalDate.now().plusDays(2L), ID, ID);

        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> mapper.toEntity(requestDto));
    }

    @Test
    void toEntity_ShouldThrowException_WhenProjectNotFound() {
        UserEntity userEntity = new UserEntity("nastya", "n@gmail.com", "12345");
        userEntity.setId(ID);

        TaskRequestDto requestDto = new TaskRequestDto("name", "description", LocalDate.now(),
                LocalDate.now().plusDays(2L), ID, ID);

        when(userRepository.findById(ID)).thenReturn(Optional.of(userEntity));
        when(projectRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> mapper.toEntity(requestDto));
    }
}
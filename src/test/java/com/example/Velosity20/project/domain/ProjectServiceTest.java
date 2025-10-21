package com.example.Velosity20.project.domain;

import com.example.Velosity20.project.db.ProjectEntity;
import com.example.Velosity20.project.db.ProjectMapper;
import com.example.Velosity20.project.db.ProjectRepository;
import com.example.Velosity20.project.dto.ProjectFilterDto;
import com.example.Velosity20.project.dto.ProjectRequestDto;
import com.example.Velosity20.project.dto.ProjectResponseDto;
import com.example.Velosity20.user.db.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    ProjectRepository repository;

    @Mock
    ProjectMapper mapper;

    @InjectMocks
    ProjectService service;

    private static final Long USER_ID = 1L;
    private static final Long PROJECT_ID_1 = 1L;
    private static final Long PROJECT_ID_2 = 2L;
    private static final String USER_NAME = "nastya";
    private static final String USER_EMAIL = "n@gmail.com";
    private static final String USER_PASSWORD = "12345";
    private static final String PROJECT_NAME_1 = "project1";
    private static final String PROJECT_NAME_2 = "project2";

    @Test
    void findAll_ShouldReturnProjects_WithPaginationAndFilter() {
        UserEntity user1 = new UserEntity(USER_NAME, USER_EMAIL, USER_PASSWORD);

        ProjectEntity project1 = new ProjectEntity(PROJECT_NAME_1, user1);
        ProjectEntity project2 = new ProjectEntity(PROJECT_NAME_2, user1);

        List<ProjectEntity> projects = List.of(project1, project2);

        ProjectResponseDto response1 = new ProjectResponseDto(PROJECT_ID_1, PROJECT_NAME_1, USER_ID);
        ProjectResponseDto response2 = new ProjectResponseDto(PROJECT_ID_2, PROJECT_NAME_2, USER_ID);

        List<ProjectResponseDto> result = List.of(response1, response2);

        ProjectFilterDto filterDto = new ProjectFilterDto(USER_ID, 5, 1);

        when(repository.findAllByFilter(eq(filterDto.userId()), any(Pageable.class))).thenReturn(projects);
        when(mapper.toResponse(project1)).thenReturn(response1);
        when(mapper.toResponse(project2)).thenReturn(response2);

        assertEquals(result, service.findAll(filterDto));
        verify(repository).findAllByFilter(eq(USER_ID), argThat((Pageable pageable) ->
                pageable.getPageSize() == 5 && pageable.getPageNumber() == 1));
        verify(mapper, times(2)).toResponse(any(ProjectEntity.class));
    }

    @Test
    void findById_ShouldReturnProject_WhenProjectExists() {
        UserEntity userEntity = new UserEntity(USER_NAME, USER_EMAIL, USER_PASSWORD);
        ProjectEntity projectEntity = new ProjectEntity(PROJECT_NAME_1, userEntity);
        ProjectResponseDto responseDto = new ProjectResponseDto(PROJECT_ID_1, PROJECT_NAME_1, USER_ID);
        when(repository.findById(PROJECT_ID_1)).thenReturn(Optional.of(projectEntity));
        when(mapper.toResponse(projectEntity)).thenReturn(responseDto);

        ProjectResponseDto result = service.findById(PROJECT_ID_1);

        assertEquals(responseDto, result);

        verify(repository).findById(PROJECT_ID_1);
        verify(mapper).toResponse(projectEntity);
    }

    @Test
    void findById_ShouldThrowException_WhenProjectNotFound() {
        when(repository.findById(PROJECT_ID_1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findById(PROJECT_ID_1));
        verify(repository).findById(PROJECT_ID_1);
    }

    @Test
    void createProject_ShouldSaveAndReturnProject() {
        UserEntity userEntity = new UserEntity(USER_NAME, USER_EMAIL, USER_PASSWORD);
        ProjectRequestDto requestDto = new ProjectRequestDto(PROJECT_NAME_1, USER_ID);
        ProjectEntity projectEntity = new ProjectEntity(PROJECT_NAME_1, userEntity);
        ProjectResponseDto responseDto = new ProjectResponseDto(PROJECT_ID_1, PROJECT_NAME_1, USER_ID);

        when(mapper.toEntity(requestDto)).thenReturn(projectEntity);
        when(repository.save(projectEntity)).thenReturn(projectEntity);
        when(mapper.toResponse(projectEntity)).thenReturn(responseDto);

        ProjectResponseDto result = service.createProject(requestDto);

        assertEquals(responseDto, result);
        verify(mapper).toEntity(requestDto);
        verify(repository).save(projectEntity);
        verify(mapper).toResponse(projectEntity);
    }

    @Test
    void deleteProject_ShouldDeleteProject_WhenProjectExists() {
        UserEntity userEntity = new UserEntity(USER_NAME, USER_EMAIL, USER_PASSWORD);
        ProjectEntity projectEntity = new ProjectEntity(PROJECT_NAME_1, userEntity);
        when(repository.findById(PROJECT_ID_1)).thenReturn(Optional.of(projectEntity));

        service.deleteProject(PROJECT_ID_1);

        verify(repository).findById(PROJECT_ID_1);
        verify(repository).deleteById(PROJECT_ID_1);
    }

    @Test
    void deleteProject_ShouldThrowException_WhenProjectNotFound() {
        when(repository.findById(PROJECT_ID_1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.deleteProject(PROJECT_ID_1));

        verify(repository).findById(PROJECT_ID_1);
        verify(repository, never()).deleteById(PROJECT_ID_1);
    }

    @Test
    void updateProject_ShouldUpdateAndReturnProject_WhenProjectExists() {
        UserEntity userEntity = new UserEntity(USER_NAME, USER_EMAIL, USER_PASSWORD);
        ProjectRequestDto requestDto = new ProjectRequestDto(PROJECT_NAME_1, USER_ID);
        ProjectEntity projectEntity = new ProjectEntity(PROJECT_NAME_1, userEntity);
        projectEntity.setId(PROJECT_ID_1);
        ProjectResponseDto responseDto = new ProjectResponseDto(PROJECT_ID_1, PROJECT_NAME_1, USER_ID);

        when(repository.findById(PROJECT_ID_1)).thenReturn(Optional.of(projectEntity));
        when(mapper.toEntity(requestDto)).thenReturn(projectEntity);
        when(repository.save(projectEntity)).thenReturn(projectEntity);
        when(mapper.toResponse(projectEntity)).thenReturn(responseDto);

        ProjectResponseDto result = service.updateProject(PROJECT_ID_1, requestDto);

        assertEquals(responseDto, result);
        verify(repository).findById(PROJECT_ID_1);
        verify(mapper).toEntity(requestDto);
        verify(repository).save(projectEntity);
        verify(mapper).toResponse(projectEntity);
    }

    @Test
    void updateProject_ShouldThrowException_WhenProjectNotFound() {
        ProjectRequestDto requestDto = new ProjectRequestDto(PROJECT_NAME_1, USER_ID);
        when(repository.findById(PROJECT_ID_1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.updateProject(PROJECT_ID_1, requestDto));

        verify(repository).findById(PROJECT_ID_1);
    }

    @Test
    void findProjectsByUserId_ShouldReturnProjects_WhenUserHasProjects() {
        UserEntity user1 = new UserEntity(USER_NAME, USER_EMAIL, USER_PASSWORD);

        ProjectEntity project1 = new ProjectEntity(PROJECT_NAME_1, user1);
        ProjectEntity project2 = new ProjectEntity(PROJECT_NAME_2, user1);

        List<ProjectEntity> projects = List.of(project1, project2);

        ProjectResponseDto response1 = new ProjectResponseDto(PROJECT_ID_1, PROJECT_NAME_1, USER_ID);
        ProjectResponseDto response2 = new ProjectResponseDto(PROJECT_ID_2, PROJECT_NAME_2, USER_ID);

        List<ProjectResponseDto> result = List.of(response1, response2);

        when(repository.findProjectsByUserId(USER_ID)).thenReturn(projects);
        when(mapper.toResponse(project1)).thenReturn(response1);
        when(mapper.toResponse(project2)).thenReturn(response2);

        assertEquals(result, service.findProjectsByUserId(USER_ID));
        verify(repository).findProjectsByUserId(USER_ID);
        verify(mapper, times(2)).toResponse(any(ProjectEntity.class));
    }
}
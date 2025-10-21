package com.example.Velosity20.project.db;

import com.example.Velosity20.project.dto.ProjectRequestDto;
import com.example.Velosity20.project.dto.ProjectResponseDto;
import com.example.Velosity20.user.db.UserEntity;
import com.example.Velosity20.user.db.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectMapperTest {

    private final Long ID = 1L;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ProjectMapper mapper;

    @Test
    void toResponse() {
        UserEntity userEntity = new UserEntity("nastya", "n@gmail.com", "12345");
        userEntity.setId(ID);
        ProjectEntity projectEntity = new ProjectEntity("project1", userEntity);
        projectEntity.setId(ID);
        ProjectResponseDto responseDto = new ProjectResponseDto(ID, "project1", ID);

        assertEquals(responseDto, mapper.toResponse(projectEntity));
    }

    @Test
    void toEntity() {
        UserEntity userEntity = new UserEntity("nastya", "n@gmail.com", "12345");
        ProjectRequestDto requestDto = new ProjectRequestDto("project1", ID);
        userEntity.setId(ID);
        ProjectEntity projectEntity = new ProjectEntity("project1", userEntity);
        projectEntity.setId(ID);

        when(userRepository.findById(ID)).thenReturn(Optional.of(userEntity));

        ProjectEntity result = mapper.toEntity(requestDto);

        assertEquals(projectEntity.getName(), result.getName());
        assertEquals(projectEntity.getUser(), result.getUser());
    }

    @Test
    void toEntity_ShouldThrowException_WhenUserNotFound() {
        ProjectRequestDto requestDto = new ProjectRequestDto("project1", ID);

        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> mapper.toEntity(requestDto));
    }
}
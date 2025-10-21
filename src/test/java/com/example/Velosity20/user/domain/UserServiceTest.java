package com.example.Velosity20.user.domain;

import com.example.Velosity20.user.db.UserEntity;
import com.example.Velosity20.user.db.UserMapper;
import com.example.Velosity20.user.db.UserRepository;
import com.example.Velosity20.user.dto.UserRequestDto;
import com.example.Velosity20.user.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    UserMapper mapper;

    @InjectMocks
    UserService service;

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity("nastya", "n@gmail.com", "12345");
        userEntity.setId(id);
        UserResponseDto userResponseDto = new UserResponseDto(1L, "nastya","n@gmail.com");

        when(repository.findById(id)).thenReturn(Optional.of(userEntity));
        when(mapper.toResponse(userEntity)).thenReturn(userResponseDto);

        UserResponseDto result = service.findById(id);

        assertEquals(userResponseDto, result);
        verify(repository).findById(id);
        verify(mapper).toResponse(userEntity);
    }

    @Test
    void findById_ShouldThrowException_WhenUserNotFound() {
        Long userId = 1L;
        when(repository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findById(userId));

        verify(repository).findById(userId);
        verify(mapper, never()).toResponse(any());
    }

    @Test
    void createUser_ShouldSaveAndReturnUser_WhenValidRequest() {
        Long id = 1L;
        UserRequestDto requestDto = new UserRequestDto("name", "email@gmail.com", "password");
        UserEntity userEntity = new UserEntity("name", "email@gmail.com", "password");
        UserResponseDto responseDto = new UserResponseDto(id, "name", "email@gmail.com");

        when(mapper.toEntity(requestDto)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(userEntity);
        when(mapper.toResponse(userEntity)).thenReturn(responseDto);

        UserResponseDto result = service.createUser(requestDto);

        assertEquals(responseDto, result);
        verify(mapper).toEntity(requestDto);
        verify(repository).save(userEntity);
        verify(mapper).toResponse(userEntity);
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity("nastya", "n@gmail.com", "123");
        userEntity.setId(userId);

        when(repository.findById(userId)).thenReturn(Optional.of(userEntity));
        service.deleteUser(userId);

        verify(repository).findById(userId);
        verify(repository).deleteById(userId);
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.deleteUser(id));

        verify(repository).findById(id);
        verify(repository, never()).deleteById(id);
    }

    @Test
    void updateUser_ShouldUpdateAndReturnUser_WhenUserExists() {
        Long id = 1L;
        UserRequestDto requestDto = new UserRequestDto("nastya", "n@gmail.com", "12345");
        UserEntity userEntity = new UserEntity("nastya", "n@gmail.com", "12345");
        UserResponseDto responseDto = new UserResponseDto(id, "nastya", "n@gmail.com");

        when(repository.findById(id)).thenReturn(Optional.of(userEntity));
        when(mapper.toEntity(requestDto)).thenReturn(userEntity);
        userEntity.setId(id);
        when(repository.save(userEntity)).thenReturn(userEntity);
        when(mapper.toResponse(userEntity)).thenReturn(responseDto);

        assertEquals(responseDto, service.updateUser(id, requestDto));
        verify(repository).findById(id);
        verify(mapper).toEntity(requestDto);
        verify(repository).save(userEntity);
        verify(mapper).toResponse(userEntity);
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserNotFound() {
        Long id = 1L;
        UserRequestDto requestDto = new UserRequestDto("nastya", "n@gmail.com", "12345");
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.updateUser(id, requestDto));
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }
}
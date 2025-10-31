package com.example.Velosity20.user.domain;

import com.example.Velosity20.user.db.UserEntity;
import com.example.Velosity20.user.db.UserMapper;
import com.example.Velosity20.user.db.UserRepository;
import com.example.Velosity20.user.dto.UserLoginDto;
import com.example.Velosity20.user.dto.UserRequestDto;
import com.example.Velosity20.user.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserMapper mapper;

    @Mock
    UserRepository repository;

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    AuthService service;

    @Test
    void register() {
        UserRequestDto requestDto = new UserRequestDto("bob", "bob", "bob@gmail.com", "bob");
        UserEntity user = new UserEntity("bob", "bob", "bob@gmail.com", "bob");
        UserResponseDto responseDto = new UserResponseDto(1L, "bob", " bob", "bob@gmail.com");

        when(repository.findByUsername(requestDto.username())).thenReturn(Optional.empty());
        when(repository.findByEmail(requestDto.email())).thenReturn(Optional.empty());
        when(repository.save(user)).thenReturn(user);
        when(mapper.toEntity(requestDto)).thenReturn(user);
        when(mapper.toResponse(user)).thenReturn(responseDto);

        UserResponseDto result = service.register(requestDto);
        assertEquals(responseDto, result);

        verify(repository).findByUsername("bob");
        verify(repository).findByEmail("bob@gmail.com");
        verify(mapper).toResponse(user);
        verify(mapper).toEntity(requestDto);
    }

    @Test
    void login() {
        UserLoginDto loginDto = new UserLoginDto("bob", "bob");
        UserEntity user = new UserEntity("bob", "bob", "bob@gmail.com", "bob");
        UserResponseDto responseDto = new UserResponseDto(1L, "bob", " bob", "bob@gmail.com");
        Authentication authentication = mock(Authentication.class);

        when(repository.findByUsername(loginDto.username())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(mapper.toResponse(user)).thenReturn(responseDto);

        UserResponseDto result = service.login(loginDto);

        assertEquals(responseDto, result);

        verify(repository).findByUsername("bob");
        verify(mapper).toResponse(user);
    }
}
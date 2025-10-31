package com.example.Velosity20.user.api;

import com.example.Velosity20.user.domain.AuthService;
import com.example.Velosity20.user.dto.UserLoginDto;
import com.example.Velosity20.user.dto.UserRequestDto;
import com.example.Velosity20.user.dto.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    AuthService service;

    @InjectMocks
    AuthController controller;

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void register() throws Exception {
        UserRequestDto requestDto = new UserRequestDto("bob", "bob", "bob@gmail.com", "bob");
        String requestDtoJson = objectMapper.writeValueAsString(requestDto);
        UserResponseDto responseDto = new UserResponseDto(1L, "bob", "bob", "bob@gmail.com");

        when(service.register(any(UserRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestDtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("bob"))
                .andExpect(jsonPath("$.username").value("bob"))
                .andExpect(jsonPath("$.email").value("bob@gmail.com"));
        verify(service, times(1)).register(any(UserRequestDto.class));

    }

    @Test
    void login() throws Exception {
        UserLoginDto loginDto = new UserLoginDto("bob", "bob");
        String loginDtoJson = objectMapper.writeValueAsString(loginDto);
        UserResponseDto responseDto = new UserResponseDto(1L, "bob", "bob", "bob@gmail.com");

        when(service.login(any(UserLoginDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("bob"))
                .andExpect(jsonPath("$.username").value("bob"))
                .andExpect(jsonPath("$.email").value("bob@gmail.com"));
        verify(service, times(1)).login(any(UserLoginDto.class));
    }
}
package com.example.Velosity20.user.api;

import com.example.Velosity20.user.domain.UserService;
import com.example.Velosity20.user.dto.UserFilterDto;
import com.example.Velosity20.user.dto.UserRequestDto;
import com.example.Velosity20.user.dto.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private Long ID = 1L;

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void findAll() throws Exception {
        List<UserResponseDto> responseDtoList = List.of(
                new UserResponseDto(1L, "nastya","n@gmail.com"),
                new UserResponseDto(2L, "kate","k@gmail.com"));
        when(service.findAll(any(UserFilterDto.class))).thenReturn(responseDtoList);

        mockMvc.perform(get("/user")
                .param("pageSize", "10")
                .param("pageNumber", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[0].name").value("nastya"))
                .andExpect(jsonPath("$[1].name").value("kate"));
        verify(service, times(1)).findAll(any(UserFilterDto.class));

    }

    @Test
    void findById() throws Exception {
        UserResponseDto responseDto = new UserResponseDto(1L, "nastya","n@gmail.com");
        when(service.findById(ID)).thenReturn(responseDto);

        mockMvc.perform(get("/user/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value("nastya"))
                .andExpect(jsonPath("$.email").value("n@gmail.com"));
        verify(service, times(1)).findById(ID);

    }

    @Test
    void createUser() throws Exception {
        UserRequestDto requestDto = new UserRequestDto("name", "email@gmail.com", "password");
        String requestJson = objectMapper.writeValueAsString(requestDto);
        UserResponseDto responseDto = new UserResponseDto(ID, "nastya","n@gmail.com");
        when(service.createUser(any(UserRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value("nastya"))
                .andExpect(jsonPath("$.email").value("n@gmail.com"));
        verify(service, times(1)).createUser(any(UserRequestDto.class));


    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/user/{id}", ID))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteUser(ID);
    }

    @Test
    void updateUser() throws Exception {
        UserRequestDto requestDto = new UserRequestDto("name", "email@gmail.com", "password");
        String requestJson = objectMapper.writeValueAsString(requestDto);
        UserResponseDto responseDto = new UserResponseDto(ID, "nastya","n@gmail.com");
        when(service.updateUser(eq(ID), any(UserRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/user/{id}", ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value("nastya"))
                .andExpect(jsonPath("$.email").value("n@gmail.com"))
                .andExpect(status().isOk());
        verify(service, times(1)).updateUser(eq(ID), any(UserRequestDto.class));
    }
}
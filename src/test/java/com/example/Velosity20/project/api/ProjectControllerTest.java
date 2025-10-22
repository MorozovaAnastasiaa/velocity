package com.example.Velosity20.project.api;

import com.example.Velosity20.project.domain.ProjectService;
import com.example.Velosity20.project.dto.ProjectFilterDto;
import com.example.Velosity20.project.dto.ProjectRequestDto;
import com.example.Velosity20.project.dto.ProjectResponseDto;
import com.example.Velosity20.user.api.UserController;
import com.example.Velosity20.user.db.UserEntity;
import com.example.Velosity20.user.domain.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockingDetails;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private ProjectService service;

    @InjectMocks
    private ProjectController controller;

    private final Long ID1 = 1L;
    private final Long ID2 = 2L;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void findAll() throws Exception {
        List<ProjectResponseDto> responseDtoList = List.of(
                new ProjectResponseDto(ID1, "project1", ID1),
                new ProjectResponseDto(ID2, "project2", ID1)
        );
        when(service.findAll(any(ProjectFilterDto.class))).thenReturn(responseDtoList);

        mockMvc.perform(get("/project")
                .param("userId", "1")
                .param("pageSize", "10")
                .param("pageNumber", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(ID1))
                .andExpect(jsonPath("$[1].id").value(ID2))
                .andExpect(jsonPath("$[0].userId").value(ID1))
                .andExpect(jsonPath("$[1].userId").value(ID1));
        verify(service, times(1)).findAll(any(ProjectFilterDto.class));
    }

    @Test
    void findById() throws Exception {
        ProjectResponseDto responseDto = new ProjectResponseDto(ID1, "project1", ID1);
        when(service.findById(ID1)).thenReturn(responseDto);

        mockMvc.perform(get("/project/{id}", ID1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID1))
                .andExpect(jsonPath("$.name").value("project1"))
                .andExpect(jsonPath("$.userId").value(ID1));
        verify(service, times(1)).findById(ID1);
    }

    @Test
    void findProjectsByUser() throws Exception {
        List<ProjectResponseDto> responseDtoList = List.of(
                new ProjectResponseDto(ID1, "project1", ID1),
                new ProjectResponseDto(ID2, "project2", ID1)
        );
        when(service.findProjectsByUserId(ID1)).thenReturn(responseDtoList);

        mockMvc.perform(get("/project/user/{id}", ID1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(ID1))
                .andExpect(jsonPath("$[1].id").value(ID2))
                .andExpect(jsonPath("$[0].userId").value(ID1))
                .andExpect(jsonPath("$[1].userId").value(ID1));
        verify(service,times(1)).findProjectsByUserId(ID1);
    }

    @Test
    void createProject() throws Exception {
        ProjectRequestDto requestDto = new ProjectRequestDto("project1", ID1);
        String requestJson = objectMapper.writeValueAsString(requestDto);
        ProjectResponseDto responseDto = new ProjectResponseDto(ID1, "project1", ID1);
        when(service.createProject(any(ProjectRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID1))
                .andExpect(jsonPath("$.name").value("project1"))
                .andExpect(jsonPath("$.userId").value(ID1));
        verify(service,times(1)).createProject(any(ProjectRequestDto.class));
    }

    @Test
    void deleteProject() throws Exception {
        mockMvc.perform(delete("/project/{id}", ID1))
                .andExpect(status().isOk());
        verify(service,times(1)).deleteProject(ID1);
    }

    @Test
    void updateProject() throws Exception {
        ProjectRequestDto requestDto = new ProjectRequestDto("project1", ID1);
        String requestJson = objectMapper.writeValueAsString(requestDto);
        ProjectResponseDto responseDto = new ProjectResponseDto(ID1, "project1", ID1);
        when(service.updateProject(eq(ID1), any(ProjectRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/project/{id}", ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID1))
                .andExpect(jsonPath("$.name").value("project1"))
                .andExpect(jsonPath("$.userId").value(ID1));
        verify(service,times(1)).updateProject(eq(ID1), any(ProjectRequestDto.class));
    }
}
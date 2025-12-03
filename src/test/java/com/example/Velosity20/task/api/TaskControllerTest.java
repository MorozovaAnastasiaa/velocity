package com.example.Velosity20.task.api;

import com.example.Velosity20.task.domain.TaskService;
import com.example.Velosity20.task.dto.TaskFilterDto;
import com.example.Velosity20.task.dto.TaskRequestDto;
import com.example.Velosity20.task.dto.TaskResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService service;

    @InjectMocks
    private TaskController controller;

    private final Long ID1 = 1L;
    private final Long ID2 = 2L;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // ← ДОБАВИТЬ ЭТУ СТРОКУ

    }

    @Test
    void findAllByFilter() throws Exception {
        List<TaskResponseDto> responseDtoList = List.of(
                new TaskResponseDto(ID1, "name1", "description1", LocalDate.now(),
                        LocalDate.now().plusDays(2L), ID1, ID2, ID1),
                new TaskResponseDto(ID2, "name2", "description2", LocalDate.now(),
                        LocalDate.now().plusDays(2L), ID1, ID2, ID1)
        );
        when(service.findAllByFilter(any(TaskFilterDto.class))).thenReturn(responseDtoList);

        mockMvc.perform(get("/task")
                .param("executorId", "1")
                .param("projectId", "1")
                .param("pageSize", "10")
                .param("pageNumber", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(ID1))
                .andExpect(jsonPath("$[1].id").value(ID2))
                .andExpect(jsonPath("$[0].executorId").value(ID1))
                .andExpect(jsonPath("$[1].executorId").value(ID1))
                .andExpect(jsonPath("$[0].projectId").value(ID2))
                .andExpect(jsonPath("$[1].projectId").value(ID2));

        verify(service,times(1)).findAllByFilter(any(TaskFilterDto.class));

    }

    @Test
    void findById() throws Exception {
        TaskResponseDto responseDto = new TaskResponseDto(ID1, "name1", "description1", LocalDate.now(),
                LocalDate.now().plusDays(2L), ID1, ID1, ID1);
        when(service.findById(ID1)).thenReturn(responseDto);

        mockMvc.perform(get("/task/{id}", ID1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID1))
                .andExpect(jsonPath("$.name").value("name1"))
                .andExpect(jsonPath("$.executorId").value(ID1))
                .andExpect(jsonPath("$.projectId").value(ID1));
        verify(service, times(1)).findById(ID1);

    }

    @Test
    void findTasksByUserId() throws Exception {
        List<TaskResponseDto> responseDtoList = List.of(
                new TaskResponseDto(ID1, "name1", "description1", LocalDate.now(),
                        LocalDate.now().plusDays(2L), ID1, ID2, ID1),
                new TaskResponseDto(ID2, "name2", "description2", LocalDate.now(),
                        LocalDate.now().plusDays(2L), ID1, ID2, ID1)
        );
        when(service.findTasksByUserId(ID1)).thenReturn(responseDtoList);

        mockMvc.perform(get("/task/user/{id}", ID1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(ID1))
                .andExpect(jsonPath("$[1].id").value(ID2))
                .andExpect(jsonPath("$[0].executorId").value(ID1))
                .andExpect(jsonPath("$[1].executorId").value(ID1))
                .andExpect(jsonPath("$[0].projectId").value(ID2))
                .andExpect(jsonPath("$[1].projectId").value(ID2));
        verify(service, times(1)).findTasksByUserId(ID1);
    }

    @Test
    void findTasksByProjectId() throws Exception {
        List<TaskResponseDto> responseDtoList = List.of(
                new TaskResponseDto(ID1, "name1", "description1", LocalDate.now(),
                        LocalDate.now().plusDays(2L), ID1, ID2, ID1),
                new TaskResponseDto(ID2, "name2", "description2", LocalDate.now(),
                        LocalDate.now().plusDays(2L), ID1, ID2, ID1)
        );
        when(service.findTasksByProjectId(ID2)).thenReturn(responseDtoList);

        mockMvc.perform(get("/task/project/{id}", ID2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(ID1))
                .andExpect(jsonPath("$[1].id").value(ID2))
                .andExpect(jsonPath("$[0].executorId").value(ID1))
                .andExpect(jsonPath("$[1].executorId").value(ID1))
                .andExpect(jsonPath("$[0].projectId").value(ID2))
                .andExpect(jsonPath("$[1].projectId").value(ID2));
        verify(service, times(1)).findTasksByProjectId(ID2);
    }

    @Test
    void createTask() throws Exception {
        TaskRequestDto requestDto = new TaskRequestDto("name1", "description1", LocalDate.now(),
                LocalDate.now().plusDays(2L), ID1, ID1, ID1);
        String requestJson = objectMapper.writeValueAsString(requestDto);
        TaskResponseDto responseDto = new TaskResponseDto(ID1, "name1", "description1", LocalDate.now(),
                LocalDate.now().plusDays(2L), ID1, ID1, ID1);
        when(service.createTask(any(TaskRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID1))
                .andExpect(jsonPath("$.name").value("name1"))
                .andExpect(jsonPath("$.executorId").value(ID1))
                .andExpect(jsonPath("$.projectId").value(ID1));
        verify(service, times(1)).createTask(any(TaskRequestDto.class));
    }

    @Test
    void deleteProject() throws Exception {
        mockMvc.perform(delete("/task/{id}", ID1))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteProject(ID1);
    }

    @Test
    void updateTask() throws Exception {
        TaskRequestDto requestDto = new TaskRequestDto("name1", "description1", LocalDate.now(),
                LocalDate.now().plusDays(2L), ID1, ID1, ID1);
        String requestJson = objectMapper.writeValueAsString(requestDto);
        TaskResponseDto responseDto = new TaskResponseDto(ID1, "name1", "description1", LocalDate.now(),
                LocalDate.now().plusDays(2L), ID1, ID1, ID1);
        when(service.updateProject(eq(ID1), any(TaskRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/task/{id}", ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID1))
                .andExpect(jsonPath("$.name").value("name1"))
                .andExpect(jsonPath("$.executorId").value(ID1))
                .andExpect(jsonPath("$.projectId").value(ID1));
        verify(service, times(1)).updateProject(eq(ID1), any(TaskRequestDto.class));
    }
}
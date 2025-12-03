package com.example.Velosity20.task.api;

import com.example.Velosity20.GlobalExceptionHandler;
import com.example.Velosity20.project.dto.ProjectRequestDto;
import com.example.Velosity20.project.dto.ProjectResponseDto;
import com.example.Velosity20.task.domain.TaskService;
import com.example.Velosity20.task.dto.TaskFilterDto;
import com.example.Velosity20.task.dto.TaskRequestDto;
import com.example.Velosity20.task.dto.TaskResponseDto;
import com.example.Velosity20.user.db.UserEntity;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findAllByFilter(
            @RequestParam(name = "executorId", required = false) Long executorId,
            @RequestParam(name = "projectId", required = false) Long projectId,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber
    ) {
        log.info("Execute method findAll()");

        TaskFilterDto filterDto = new TaskFilterDto(
                executorId,
                projectId,
                pageSize,
                pageNumber
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByFilter(filterDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> findById(
            @PathVariable Long id
    ) {
        log.info("Execute method findById() with id = " + id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<TaskResponseDto>> findTasksByUserId(
            @PathVariable Long id
    ) {
        log.info("Execute method findTaskByUserId() with id = " + id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findTasksByUserId(id));
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<TaskResponseDto>> findTasksByProjectId(
            @PathVariable Long id
    ) {
        log.info("Execute method findTasksByProjectId() with id = " + id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findTasksByProjectId(id));
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(
            @RequestBody @Valid TaskRequestDto task
    ) {
        log.info("Execute method createTask()");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createTask(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long id
    ) {
        log.info("Execute method deleteProject() with id = " + id);

        service.deleteProject(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long id,
            @RequestBody @Valid TaskRequestDto project
    ) {
        log.info("Execute method updateTask() with id = " + id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateProject(id, project));
    }
}

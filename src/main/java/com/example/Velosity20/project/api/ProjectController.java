package com.example.Velosity20.project.api;

import com.example.Velosity20.GlobalExceptionHandler;
import com.example.Velosity20.project.dto.ProjectFilterDto;
import com.example.Velosity20.project.dto.ProjectRequestDto;
import com.example.Velosity20.project.dto.ProjectResponseDto;
import com.example.Velosity20.project.domain.ProjectService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> findAll(
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber
    ) {
        log.info("Execute method findAll()");

        ProjectFilterDto filterDto = new ProjectFilterDto(
                userId,
                pageSize,
                pageNumber
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll(filterDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> findById(
            @PathVariable Long id
    ) {
        log.info("Execute method findById() with id = " + id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ProjectResponseDto>> findProjectsByUser(
            @PathVariable Long id
    ) {
        log.info("Execute method findProjectsByUser() with id = " + id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findProjectsByUserId(id));
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(
            @RequestBody @Valid ProjectRequestDto project
    ) {
        log.info("Execute method findById()");


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createProject(project));
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
    public ResponseEntity<ProjectResponseDto> updateProject(
            @PathVariable Long id,
            @RequestBody @Valid ProjectRequestDto project
    ) {
        log.info("Execute method updateProject() with id = " + id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateProject(id, project));
    }
}

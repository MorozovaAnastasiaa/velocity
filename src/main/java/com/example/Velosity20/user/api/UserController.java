package com.example.Velosity20.user.api;

import com.example.Velosity20.GlobalExceptionHandler;
import com.example.Velosity20.user.domain.UserService;
import com.example.Velosity20.user.dto.UserFilterDto;
import com.example.Velosity20.user.dto.UserRequestDto;
import com.example.Velosity20.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber
    ) {
        log.info("Execute method findAll()");

        UserFilterDto filterDto = new UserFilterDto(
                pageSize,
                pageNumber
        );

        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(filterDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        log.info("Execute method findById() with id = " + id);

        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto user) {
        log.info("Execute method createUser()");

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Execute method deleteUser() with id = " + id);

        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDto user) {
        log.info("Execute method updateUser() with id = " + id);

        return ResponseEntity.status(HttpStatus.OK).body(service.updateUser(id, user));
    }
}

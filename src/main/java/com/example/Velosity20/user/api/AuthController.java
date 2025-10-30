package com.example.Velosity20.user.api;

import com.example.Velosity20.GlobalExceptionHandler;
import com.example.Velosity20.user.domain.AuthService;
import com.example.Velosity20.user.dto.UserLoginDto;
import com.example.Velosity20.user.dto.UserRequestDto;
import com.example.Velosity20.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRequestDto requestDto) {
        log.info("New user has registered!");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.register(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserLoginDto loginDto) {
        log.info("New user has logged in!");

        return ResponseEntity.status(HttpStatus.OK).body(service.login(loginDto));
    }
}

package com.example.Velosity20.user.domain;

import com.example.Velosity20.user.db.UserEntity;
import com.example.Velosity20.user.db.UserMapper;
import com.example.Velosity20.user.db.UserRepository;
import com.example.Velosity20.user.dto.UserLoginDto;
import com.example.Velosity20.user.dto.UserRequestDto;
import com.example.Velosity20.user.dto.UserResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repository, UserMapper mapper, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
    }

    public UserResponseDto register(UserRequestDto requestDto) {
        if (repository.findByUsername(requestDto.username()).isPresent()) {
            throw new IllegalArgumentException("User with username " + requestDto.username() + " already exists");
        }

        if (repository.findByEmail(requestDto.email()).isPresent()) {
            throw new IllegalArgumentException("Email " + requestDto.email() + " is already used");
        }

        return mapper.toResponse(repository.save(mapper.toEntity(requestDto)));
    }

    public UserResponseDto login(UserLoginDto loginDto) {
        UserEntity user = repository.findByUsername(loginDto.username()).orElseThrow(() ->
                new BadCredentialsException("User does not exist"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return mapper.toResponse(user);
    }
}

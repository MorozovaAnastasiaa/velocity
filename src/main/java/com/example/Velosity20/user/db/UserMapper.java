package com.example.Velosity20.user.db;

import com.example.Velosity20.user.dto.UserRequestDto;
import com.example.Velosity20.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto toResponse(UserEntity user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public UserEntity toEntity(UserRequestDto requestDto) {
        return new UserEntity(
                requestDto.name(),
                requestDto.username(),
                requestDto.email(),
                requestDto.password()
        );
    }
}

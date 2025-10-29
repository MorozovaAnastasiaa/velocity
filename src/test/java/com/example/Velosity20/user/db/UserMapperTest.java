package com.example.Velosity20.user.db;

import com.example.Velosity20.user.dto.UserRequestDto;
import com.example.Velosity20.user.dto.UserResponseDto;
import org.aspectj.weaver.patterns.IVerificationRequired;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    private final Long ID = 1L;

    @Test
    void toResponse() {
        UserEntity userEntity = new UserEntity("nastya",  "nastya", "n@gmail.com", "12345");
        userEntity.setId(ID);
        UserResponseDto userResponseDto = new UserResponseDto(1L, "nastya", "nastya", "n@gmail.com");

        assertEquals(userResponseDto, mapper.toResponse(userEntity));

    }

    @Test
    void toEntity() {
        UserRequestDto requestDto = new UserRequestDto("nastya",  "nastya", "n@gmail.com", "12345");
        UserEntity expectedEntity = new UserEntity("nastya",  "nastya", "n@gmail.com", "12345");

        UserEntity result = mapper.toEntity(requestDto);

        assertEquals(expectedEntity.getName(), result.getName());
        assertEquals(expectedEntity.getEmail(), result.getEmail());
        assertEquals(expectedEntity.getPassword(), result.getPassword());
    }
}
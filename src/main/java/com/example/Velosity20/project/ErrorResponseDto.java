package com.example.Velosity20.project;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        String message,
        String description,
        LocalDateTime errorTime
) {
}

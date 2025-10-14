package com.example.Velosity20.project.dto;

import jakarta.validation.constraints.NotEmpty;

public record ProjectRequestDto(
        @NotEmpty
        String name,

        Long userId
) {
}

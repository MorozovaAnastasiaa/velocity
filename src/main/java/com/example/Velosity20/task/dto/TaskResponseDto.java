package com.example.Velosity20.task.dto;

import java.time.LocalDate;

public record TaskResponseDto(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate deadline,
        Long executorId,
        Long projectId
) {
}

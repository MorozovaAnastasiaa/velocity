package com.example.Velosity20.task.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskRequestDto(

        @NotEmpty
        String name,

        String description,

        @FutureOrPresent
        LocalDate startDate,

        @FutureOrPresent
        LocalDate deadline,

        Long executorId,

        Long projectId,

        Long columnId
) {
}

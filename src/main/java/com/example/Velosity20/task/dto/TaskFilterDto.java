package com.example.Velosity20.task.dto;

public record TaskFilterDto(
        Long executorId,
        Long projectId,
        Integer pageSize,
        Integer pageNumber
) {
}

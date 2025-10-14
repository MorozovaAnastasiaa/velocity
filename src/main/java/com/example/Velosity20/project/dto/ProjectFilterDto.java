package com.example.Velosity20.project.dto;

public record ProjectFilterDto(
        Long userId,
        Integer pageSize,
        Integer pageNumber
) {
}

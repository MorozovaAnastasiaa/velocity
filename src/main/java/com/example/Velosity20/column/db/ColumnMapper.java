package com.example.Velosity20.column.db;

import com.example.Velosity20.column.dto.ColumnResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ColumnMapper {
    public ColumnResponseDto toResponse(ColumnEntity entity) {
        return new ColumnResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getProject().getId()
        );
    }
}

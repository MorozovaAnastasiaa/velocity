package com.example.Velosity20.column.db;

import com.example.Velosity20.column.dto.ColumnRequestDto;
import com.example.Velosity20.column.dto.ColumnResponseDto;
import com.example.Velosity20.project.db.ProjectEntity;
import com.example.Velosity20.project.db.ProjectRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ColumnMapper {
    private final ProjectRepository projectRepo;

    public ColumnMapper(ProjectRepository repository) {
        this.projectRepo = repository;
    }

    public ColumnResponseDto toResponse(ColumnEntity entity) {
        return new ColumnResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getProject().getId()
        );
    }

    public ColumnEntity toEntity(ColumnRequestDto requestDto) {
        ProjectEntity project = projectRepo.findById(requestDto.projectId())
                .orElseThrow(NoSuchElementException::new);

        return new ColumnEntity(
                requestDto.name(),
                project
        );
    }
}

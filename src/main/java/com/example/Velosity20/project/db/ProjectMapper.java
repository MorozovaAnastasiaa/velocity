package com.example.Velosity20.project.db;

import com.example.Velosity20.project.db.ProjectEntity;
import com.example.Velosity20.project.dto.ProjectRequestDto;
import com.example.Velosity20.project.dto.ProjectResponseDto;
import com.example.Velosity20.user.db.UserEntity;
import com.example.Velosity20.user.db.UserRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ProjectMapper {
    public final UserRepository userRepository;

    public ProjectMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProjectResponseDto toResponse(ProjectEntity project) {
        return new ProjectResponseDto(
                project.getId(),
                project.getName(),
                project.getUser().getId()
        );
    }

    public ProjectEntity toEntity(ProjectRequestDto requestDto) {
        UserEntity user = userRepository.findById(requestDto.userId())
                .orElseThrow(() -> new NoSuchElementException());

        return new ProjectEntity(
                requestDto.name(),
                user
        );
    }
}

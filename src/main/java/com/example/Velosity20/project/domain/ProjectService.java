package com.example.Velosity20.project.domain;

import com.example.Velosity20.project.db.*;
import com.example.Velosity20.project.db.ProjectMapper;
import com.example.Velosity20.project.dto.ProjectFilterDto;
import com.example.Velosity20.project.dto.ProjectRequestDto;
import com.example.Velosity20.project.dto.ProjectResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public List<ProjectResponseDto> findAll(ProjectFilterDto filterDto) {

        int pageSize = filterDto.pageSize() != null ? filterDto.pageSize() : 100;
        int pageNumber = filterDto.pageNumber() != null ? filterDto.pageNumber() : 0;

        Pageable pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);

        return projectRepository.findAllByFilter(filterDto.userId(), pageable).stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    public ProjectResponseDto findById(Long id) {
        ProjectEntity project = projectRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return projectMapper.toResponse(project);
    }

    public ProjectResponseDto createProject(ProjectRequestDto project) {
        ProjectEntity projectEntity = projectMapper.toEntity(project);
        return projectMapper.toResponse(projectRepository.save(projectEntity));
    }

    public void deleteProject(Long id) {
        if (projectRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Element with id = " + id + "not found");
        }

        projectRepository.deleteById(id);
    }

    public ProjectResponseDto updateProject(Long id, ProjectRequestDto project) {
        if (projectRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Element with id = " + id + " not found");
        }

        ProjectEntity projectToUpdate = projectMapper.toEntity(project);
        projectToUpdate.setId(id);
        return projectMapper.toResponse(projectRepository.save(projectToUpdate));
    }

    public List<ProjectResponseDto> findProjectsByUserId(Long id) {
        return projectRepository.findProjectsByUserId(id).stream()
                .map(projectMapper::toResponse)
                .toList();
    }
}

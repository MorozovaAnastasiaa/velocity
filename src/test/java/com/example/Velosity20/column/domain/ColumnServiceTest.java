package com.example.Velosity20.column.domain;

import com.example.Velosity20.column.db.ColumnEntity;
import com.example.Velosity20.column.db.ColumnMapper;
import com.example.Velosity20.column.db.ColumnRepository;
import com.example.Velosity20.column.dto.ColumnRequestDto;
import com.example.Velosity20.column.dto.ColumnResponseDto;
import com.example.Velosity20.project.db.ProjectEntity;
import com.example.Velosity20.user.db.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ColumnServiceTest {

    @Mock
    ColumnRepository repository;

    @Mock
    ColumnMapper mapper;

    @InjectMocks
    ColumnService service;

    @Test
    void findAll() {
    }

    @Test
    void findById() {
        ColumnEntity columnEntity = createColumnEntity();
        ColumnResponseDto responseDto = createColumnResponseDto();

        when(repository.findById(1L)).thenReturn(Optional.of(columnEntity));
        when(mapper.toResponse(columnEntity)).thenReturn(responseDto);

        ColumnResponseDto result = service.findById(1L);
        assertEquals(responseDto, result);
        verify(repository).findById(1L);
        verify(mapper).toResponse(columnEntity);
    }

    @Test
    void createColumn() {
        ColumnEntity columnEntity = createColumnEntity();
        ColumnRequestDto requestDto = createColumnRequestDto();
        ColumnResponseDto responseDto = createColumnResponseDto();

        when(mapper.toEntity(requestDto)).thenReturn(columnEntity);
        when(repository.save(columnEntity)).thenReturn(columnEntity);
        when(mapper.toResponse(columnEntity)).thenReturn(responseDto);

        ColumnResponseDto result = service.createColumn(requestDto);

        assertEquals(responseDto, result);
        verify(mapper).toEntity(requestDto);
        verify(repository).save(columnEntity);
        verify(mapper).toResponse(columnEntity);
    }

    @Test
    void deleteColumn() {
        ColumnEntity columnEntity = createColumnEntity();

        when(repository.findById(1L)).thenReturn(Optional.of(columnEntity));

        service.deleteColumn(1L);

        verify(repository).findById(1L);
        verify(repository).deleteById(1L);

    }

    @Test
    void updateColumn() {
        ColumnEntity columnEntity = createColumnEntity();
        ColumnRequestDto requestDto = createColumnRequestDto();
        ColumnResponseDto responseDto = createColumnResponseDto();

        when(repository.findById(1L)).thenReturn(Optional.of(columnEntity));
        when(mapper.toEntity(requestDto)).thenReturn(columnEntity);
        when(repository.save(columnEntity)).thenReturn(columnEntity);
        when(mapper.toResponse(columnEntity)).thenReturn(responseDto);

        ColumnResponseDto result = service.updateColumn(1L, requestDto);

        assertEquals(responseDto, result);
        verify(repository).findById(1L);
        verify(repository).save(columnEntity);

    }

    @Test
    void findAllByProjectId() {
        ColumnEntity columnEntity1 = createColumnEntity();
        columnEntity1.setId(1L);
        ColumnEntity columnEntity2 = createColumnEntity();
        columnEntity2.setId(2L);
        List<ColumnEntity> columns = List.of(columnEntity1, columnEntity2);

        ColumnResponseDto responseDto1 = createColumnResponseDto();
        ColumnResponseDto responseDto2 = createColumnResponseDto();
        List<ColumnResponseDto> responses = List.of(responseDto1, responseDto2);

        when(repository.findAllByProjectId(1L)).thenReturn(columns);
        when(mapper.toResponse(columnEntity1)).thenReturn(responseDto1);
        when(mapper.toResponse(columnEntity2)).thenReturn(responseDto2);

        List<ColumnResponseDto> results = service.findAllByProjectId(1L);

        assertEquals(responses, results);
        verify(repository).findAllByProjectId(1L);
        verify(mapper).toResponse(columnEntity1);
        verify(mapper).toResponse(columnEntity2);
    }

    private ColumnEntity createColumnEntity() {
        UserEntity userEntity = new UserEntity("nastya", "nastya", "n@gmail.com", "12345");
        ProjectEntity projectEntity = new ProjectEntity("project1", userEntity);
        ColumnEntity columnEntity = new ColumnEntity("todo", projectEntity);
        return columnEntity;
    }

    private ColumnRequestDto createColumnRequestDto() {
        ColumnRequestDto requestDto = new ColumnRequestDto("todo", 1L);
        return requestDto;
    }

    private ColumnResponseDto createColumnResponseDto() {
        ColumnResponseDto responseDto = new ColumnResponseDto(1L, "todo", 1L);
        return responseDto;
    }
}
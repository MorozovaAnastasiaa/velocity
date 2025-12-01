package com.example.Velosity20.column.domain;

import com.example.Velosity20.column.db.ColumnEntity;
import com.example.Velosity20.column.db.ColumnMapper;
import com.example.Velosity20.column.db.ColumnRepository;
import com.example.Velosity20.column.dto.ColumnRequestDto;
import com.example.Velosity20.column.dto.ColumnResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ColumnService {
    private final ColumnRepository repository;
    private final ColumnMapper mapper;

    public ColumnService(ColumnRepository repository, ColumnMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ColumnResponseDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ColumnResponseDto findById(Long id) {
        ColumnEntity column = repository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return mapper.toResponse(column);
    }

    public ColumnResponseDto createColumn(ColumnRequestDto requestDto) {
        ColumnEntity column = mapper.toEntity(requestDto);
        return mapper.toResponse(repository.save(column));
    }

    public void deleteColumn(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Element with id = " + id + " not found");
        }
        repository.deleteById(id);
    }

    public ColumnResponseDto updateColumn(Long id, ColumnRequestDto requestDto) {
        if (repository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Element with id = " + id + " not found");
        }
        ColumnEntity updatedColumn = mapper.toEntity(requestDto);
        updatedColumn.setId(id);

        return mapper.toResponse(repository.save(updatedColumn));
    }

    public List<ColumnResponseDto> findAllByProjectId(Long id) {
        return repository.findAllByProjectId(id).stream()
                .map(mapper::toResponse)
                .toList();
    }
}

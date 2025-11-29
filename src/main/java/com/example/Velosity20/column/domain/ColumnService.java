package com.example.Velosity20.column.domain;

import com.example.Velosity20.column.db.ColumnEntity;
import com.example.Velosity20.column.db.ColumnMapper;
import com.example.Velosity20.column.db.ColumnRepository;
import com.example.Velosity20.column.dto.ColumnResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

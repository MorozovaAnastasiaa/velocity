package com.example.Velosity20.column.api;

import com.example.Velosity20.column.domain.ColumnService;
import com.example.Velosity20.column.dto.ColumnResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/column")
public class ColumnController {
    private final ColumnService service;

    public ColumnController(ColumnService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ColumnResponseDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }
}

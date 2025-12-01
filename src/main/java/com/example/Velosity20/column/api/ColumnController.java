package com.example.Velosity20.column.api;

import com.example.Velosity20.GlobalExceptionHandler;
import com.example.Velosity20.column.domain.ColumnService;
import com.example.Velosity20.column.dto.ColumnRequestDto;
import com.example.Velosity20.column.dto.ColumnResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/column")
public class ColumnController {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ColumnService service;

    public ColumnController(ColumnService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ColumnResponseDto>> findAll() {
        log.info("Execute method List<ColumnResponseDto> findAll()");
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColumnResponseDto> findById(
            @PathVariable Long id
    ) {
        log.info("Execute method ColumnResponseDto findById()");

        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findById(id));
    }
    
    @GetMapping("/project/{id}")
    public ResponseEntity<List<ColumnResponseDto>> findAllByProject(
            @PathVariable Long id
    ) {
        log.info("Execute method List<ColumnResponseDto> findAllByProject()");
        
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAllByProjectId(id));

    }
    

    @PostMapping
    public ResponseEntity<ColumnResponseDto> createColumn(
            @RequestBody ColumnRequestDto requestDto
    ) {
        log.info("Execute method ColumnResponseDto createColumn()");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createColumn(requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColumn(
            @PathVariable Long id
    ) {
        log.info("Execute method ResponseEntity<Void> deleteColumn()");
        service.deleteColumn(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColumnResponseDto> updateColumn(
            @PathVariable Long id,
            @RequestBody ColumnRequestDto requestDto
    ) {
        log.info("Execute method ResponseEntity<ColumnResponseDto> updateColumn()");

        return ResponseEntity.status(HttpStatus.OK)
                .body(service.updateColumn(id, requestDto));
    }
}

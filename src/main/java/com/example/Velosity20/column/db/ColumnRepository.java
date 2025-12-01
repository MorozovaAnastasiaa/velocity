package com.example.Velosity20.column.db;

import com.example.Velosity20.column.dto.ColumnResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {

    @Query("""
            SELECT c 
            FROM ColumnEntity c
            WHERE c.project.id = :id
            """)
    List<ColumnEntity> findAllByProjectId(
            @Param("id") Long id
    );
}

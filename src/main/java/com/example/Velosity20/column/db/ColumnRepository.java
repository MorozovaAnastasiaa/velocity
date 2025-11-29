package com.example.Velosity20.column.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {
}

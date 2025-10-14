package com.example.Velosity20.task.db;

import com.example.Velosity20.task.dto.TaskResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query("""
            SELECT t
            FROM TaskEntity t
            WHERE (:executor IS NULL OR t.executor.id = :executor)
            AND (:project IS NULL OR t.project.id = :project)
            """)
    List<TaskEntity> findAllByFilter(
            @Param(value = "executor") Long executor,
            @Param(value = "project") Long project,
            Pageable pageable
    );

    @Query("""
            SELECT t
            FROM TaskEntity t
            WHERE t.executor.id = :id
            """)
    List<TaskEntity> findTasksByUserId(
            @Param("id") Long id
    );

    @Query("""
            SELECT t
            FROM TaskEntity t
            WHERE t.project.id = :id
            """)
    List<TaskEntity> findTasksByProjectId(
            @Param("id") Long id
    );
}

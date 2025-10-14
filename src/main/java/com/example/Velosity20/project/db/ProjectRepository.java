package com.example.Velosity20.project.db;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    @Query("""
            SELECT p
            FROM ProjectEntity p
            WHERE p.userEntity.id = :id
            """)
    List<ProjectEntity> findProjectsByUserId(
            @Param("id") Long id
    );

    @Query("""
            SELECT p
            FROM ProjectEntity p
            WHERE :userId IS NULL OR p.userEntity.id = :userId
            """)
    List<ProjectEntity> findAllByFilter(
            @Param(value = "userId") Long userId,
            Pageable pageable
    );
}

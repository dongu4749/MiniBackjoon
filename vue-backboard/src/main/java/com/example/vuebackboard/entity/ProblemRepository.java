package com.example.vuebackboard.entity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface ProblemRepository extends JpaRepository<ProblemEntity, Long> {
    Page<ProblemEntity> findAllByOrderByIdxDesc(Pageable pageable);
}

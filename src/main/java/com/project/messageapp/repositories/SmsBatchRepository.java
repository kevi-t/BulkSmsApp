package com.project.messageapp.repositories;

import com.project.messageapp.models.SmsBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmsBatchRepository extends JpaRepository<SmsBatch,Long> {
    List<SmsBatch> findAllByStatusOrderByCreatDateDesc(String status);
}
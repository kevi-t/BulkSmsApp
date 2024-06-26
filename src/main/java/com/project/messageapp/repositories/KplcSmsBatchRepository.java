package com.project.messageapp.repositories;

import com.project.messageapp.models.KplcSmsBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KplcSmsBatchRepository extends JpaRepository<KplcSmsBatch, Long> {
      KplcSmsBatch findKplcSmsBatchByBatchId(Long batchId);
}
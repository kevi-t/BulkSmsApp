package com.project.messageapp.repositories;

import com.project.messageapp.models.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch,Long> {
    List<Batch> findAllByStatusOrderByDateCreatedDesc(String status);
}
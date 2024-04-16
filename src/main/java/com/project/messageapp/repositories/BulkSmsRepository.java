package com.project.messageapp.repositories;

import com.project.messageapp.models.BulkSms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulkSmsRepository extends JpaRepository<BulkSms, Long> {
    List<BulkSms> findAllByStatusOrderByCreateDateDesc(String status);
}
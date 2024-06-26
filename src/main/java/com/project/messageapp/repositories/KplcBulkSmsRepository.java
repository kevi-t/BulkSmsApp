package com.project.messageapp.repositories;

import com.project.messageapp.models.KplcBulkSms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KplcBulkSmsRepository extends JpaRepository<KplcBulkSms, Long> {
}
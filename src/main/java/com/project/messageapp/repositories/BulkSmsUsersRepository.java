package com.project.messageapp.repositories;

import com.project.messageapp.models.BulkSmsUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulkSmsUsersRepository extends JpaRepository<BulkSmsUsers, Long> {
    BulkSmsUsers findBulkSmsUsersByEmail(String email);
    BulkSmsUsers findBulkSmsUsersByStaffNo(String staffNo);
}
package com.project.messageapp.repositories;

import com.project.messageapp.models.SmsUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsUsersRepository extends JpaRepository<SmsUsers, Long> {
    SmsUsers findSmsUsersByUserEmail(String userEmail);
    SmsUsers findSmsUsersByUserId(Long userId);
}
package com.project.messageapp.repositories;

import com.project.messageapp.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByStatusOrderByCreatedAtDesc(String status);
}
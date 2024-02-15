package com.project.messageapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long smsId;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String msg;
    @NotNull
    private String recipientNumber;
    @NotNull
    private String senderAccountNumber;
    @NotNull
    private String status; // e.g., "SENT", "FAILED"

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime scheduleAt;
}
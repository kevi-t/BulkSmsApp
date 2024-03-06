package com.project.messageapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msgId;

//    @ManyToOne
//    @JoinColumn(name = "batch_id")
//    private Batch batch;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Users user;
    private Long batchId;
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

    private LocalDateTime dateSent;
    private LocalDateTime updatedAt;
    private LocalDateTime scheduleAt;

}
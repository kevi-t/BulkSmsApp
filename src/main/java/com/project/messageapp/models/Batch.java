package com.project.messageapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

    @Column(nullable = false)
    private String messageDescription;

    private Long userId;

    private String status;

//    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL)
//    private List<Message> messages;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    private LocalDateTime dateSent;
}

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
public class SmsBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batch_seq_generator")
    @SequenceGenerator(name="batch_seq_generator", sequenceName="SEQ_BATCH_ID", allocationSize = 1)
    private Long batchId;

    @NotNull
    private String batchDescription;
    @NotNull
    private Long userId;
    @NotNull
    private String status;

//    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL)
//    private List<Message> messages;

    @CreationTimestamp
    private LocalDateTime creatDate;
    private LocalDateTime dateSent;
}

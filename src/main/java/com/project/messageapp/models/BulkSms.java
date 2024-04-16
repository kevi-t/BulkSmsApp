package com.project.messageapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BulkSms {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sms_seq_generator")
    @SequenceGenerator(name="sms_seq_generator", sequenceName="SEQ_SMS_ID", allocationSize = 1)
    private Long smsId;
    private Long userId;
    private Long batchId;
    private String messages;
    private String phoneNumber;
    private LocalDateTime createDate;
    private LocalDateTime dateSent;
    private String status;
}

package com.project.messageapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KPLC_BULK_SMS")
public class KplcBulkSms {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sms_seq_generator")
    @SequenceGenerator(name = "sms_seq_generator", sequenceName = "SEQ_SMS_ID", allocationSize = 1)
    @Column(name = "SMS_ID", nullable = false)
    private Long smsId;

    @Column(name = "BATCH_ID", nullable = false)
    private Long batchId;

    @Column(name = "MESSAGES", nullable = false, length = 500)
    private String messages;

    @Column(name = "PHONE_NUMBER", nullable = false, length = 64)
    private String phoneNumber;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Column(name = "DATE_SENT")
    @Temporal(TemporalType.DATE)
    private Date dateSent;

    @Column(name = "STATUS", nullable = false, length = 20)
    private String status;
}
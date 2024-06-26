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
@Table(name = "KPLC_SMS_BATCH")
public class KplcSmsBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batch_seq_generator")
    @SequenceGenerator(name="batch_seq_generator", sequenceName="SEQ_BATCH_ID", allocationSize = 1)
    @Column(name = "BATCH_ID", nullable = false)
    private Long batchId;

    @Column(name = "BATCH_DESCRIPTION", nullable = false, length = 50)
    private String batchDescription;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Column(name = "DATE_SENT")
    @Temporal(TemporalType.DATE)
    private Date dateSent;

    @Column(name = "STAFF_NO", length = 25)
    private String staffNo;

    @Column(name = "STATUS", length = 15)
    private String status;
}
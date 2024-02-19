package com.project.messageapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Validated
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class FilteredList {
    private String msg;
    private String recipientNumber;
    private String senderAccountNumber;
    private LocalDateTime createdAt;
}

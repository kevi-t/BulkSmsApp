package com.project.messageapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomersDTO {
    private Long customerId;
    private String accountNumber;
    private String phoneNumber;
    private String fullName;
}
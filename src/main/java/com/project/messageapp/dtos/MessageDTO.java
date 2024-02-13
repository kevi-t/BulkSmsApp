package com.project.messageapp.dtos;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class MessageDTO {
    private String [] recipientNumbers;
    private String message;
}
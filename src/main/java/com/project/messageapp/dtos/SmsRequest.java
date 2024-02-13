package com.project.messageapp.dtos;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class SmsRequest {

    private String to;
    private String message;
}
package com.project.messageapp.dtos;

import jakarta.validation.constraints.NotEmpty;
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
public class MessageDTO {
    @NotEmpty(message = "Field description is empty")
    private String messageDescription;
    @NotEmpty(message = "Field description is empty")
    private String message;
//    private String [] recipientNumbers;
//    private String message;
}
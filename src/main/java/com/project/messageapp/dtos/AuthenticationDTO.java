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
public class AuthenticationDTO {
    @NotEmpty(message = "Field accountNumber should is empty")
    private String accountNumber;
    @NotEmpty(message = "Field password is empty")
    private String password;
}
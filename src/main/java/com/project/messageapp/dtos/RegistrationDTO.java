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
public class RegistrationDTO {
    @NotEmpty(message = "Field staff no is empty")
    private String staffNo;
    @NotEmpty(message = "Field first name is empty")
    private String firstName;
    @NotEmpty(message = "Field last name is empty")
    private String lastName;
    @NotEmpty(message = "Field email is empty")
    private String email;
    @NotEmpty(message = "Field password is empty")
    private String password;

}
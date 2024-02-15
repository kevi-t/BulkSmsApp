package com.project.messageapp.responses;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UniversalResponse {
    private String message;
    private Object data;
    private String status;
}
package com.project.messageapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sessions {
    @Id
    private Long sessionId;

    private String tokenId; // JWT token ID (can also be a UUID)

    private Long userId; // User ID associated with the session

    private LocalDateTime expirationDate; // Expiration time of the session
}

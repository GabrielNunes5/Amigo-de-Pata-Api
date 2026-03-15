package com.example.amigo_de_patas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "passwordResetToken")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tokenID;

    private String resetToken;

    @ManyToOne(fetch = FetchType.LAZY)
    private Adopter adopter;

    private LocalDateTime expiresAt;

    private Boolean valid;
}

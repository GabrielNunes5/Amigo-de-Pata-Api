package com.example.amigo_de_patas.repository;

import com.example.amigo_de_patas.model.Adopter;
import com.example.amigo_de_patas.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {
    Optional<PasswordResetToken> findByResetToken(String resetToken);

    List<PasswordResetToken> findByAdopter(Adopter adopter);
}

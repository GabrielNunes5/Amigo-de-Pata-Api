package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.dto.request.ForgotPasswordRequest;
import com.example.amigo_de_patas.dto.request.ResetPasswordRequest;
import com.example.amigo_de_patas.exceptions.BadRequestException;
import com.example.amigo_de_patas.exceptions.TooManyRequestsException;
import com.example.amigo_de_patas.model.Adopter;
import com.example.amigo_de_patas.model.PasswordResetToken;
import com.example.amigo_de_patas.repository.AdopterRepository;
import com.example.amigo_de_patas.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final AdopterRepository adopterRepository;
    private final EmailService emailService;
    private final AdopterService adopterService;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();
    private final AttemptsService attemptsService;

    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository, AdopterRepository adopterRepository, EmailService emailService, AdopterService adopterService, AttemptsService attemptsService) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.adopterRepository = adopterRepository;
        this.emailService = emailService;
        this.adopterService = adopterService;
        this.attemptsService = attemptsService;
    }

    private String makeResetToken(){
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request, String ip) {
        if (attemptsService.isBlocked(ip)){
            throw new TooManyRequestsException("Atingiu o limite de tentativas. Tente novamente em breve");
        }

        attemptsService.registerAttempt(ip);

        adopterRepository.findAdopterByAdopterEmail(request.getAdopterEmail())
                .ifPresent(adopter -> {
                    List<PasswordResetToken> resetTokens = passwordResetTokenRepository.findByAdopter(adopter);
                    resetTokens.forEach(token -> token.setValid(false));
                    passwordResetTokenRepository.saveAll(resetTokens);

                    PasswordResetToken passwordResetToken = new PasswordResetToken();
                    passwordResetToken.setAdopter(adopter);
                    passwordResetToken.setResetToken(makeResetToken());
                    passwordResetToken.setValid(true);
                    passwordResetToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
                    passwordResetTokenRepository.save(passwordResetToken);

                    emailService.sendPasswordResetEmail(request.getAdopterEmail(),passwordResetToken.getResetToken());
                });
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request, String resetToken) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository
                .findByResetToken(resetToken)
                .orElseThrow(() -> new BadRequestException("Token não encontrado"));

        if (passwordResetToken.getValid().equals(false)) {
            throw new BadRequestException("Token inválido");
        }
        if (passwordResetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            passwordResetToken.setValid(false);
            throw new BadRequestException("Token expirado");
        }
        if (!request.getAdopterPassword().equals(request.getAdopterConfirmPassword())) {
            throw new BadRequestException("As senhas devem ser iguais");
        }

        Adopter adopter = passwordResetToken.getAdopter();
        adopterService.updateAdopterPassword(adopter, request.getAdopterPassword());
        passwordResetToken.setValid(false);

    }
}

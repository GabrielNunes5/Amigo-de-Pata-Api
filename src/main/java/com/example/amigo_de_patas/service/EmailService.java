package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    public EmailService(JavaMailSender mailSender, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String email, String resetToken) {
        String resetUrl = baseUrl + "reset-password?resetToken=" + resetToken;
        String body = "Clique no link abaixo para alterar sua senha.\n" + resetUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText(body);

        mailSender.send(message);

    }
}

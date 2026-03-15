package com.example.amigo_de_patas.service;

import brevo.ApiClient;
import brevo.ApiException;
import brevo.Configuration;
import brevoApi.TransactionalEmailsApi;
import brevoModel.SendSmtpEmail;
import brevoModel.SendSmtpEmailSender;
import brevoModel.SendSmtpEmailTo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.from.email}")
    private String fromEmail;

    @Value("${app.base-url}")
    private String baseUrl;

    public void sendPasswordResetEmail(String email, String resetToken) {
        String resetUrl = baseUrl + "reset-password?token=" + resetToken;
        String body = "Clique no link abaixo para alterar sua senha:\n" + resetUrl;

        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setApiKey(apiKey);

        TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();

        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.setSubject("Recuperação de Senha");
        sendSmtpEmail.setTextContent(body);
        sendSmtpEmail.setSender(new SendSmtpEmailSender().name("Amigo de Patas").email(fromEmail));
        sendSmtpEmail.setTo(List.of(new SendSmtpEmailTo().email(email)));

        try {
            apiInstance.sendTransacEmail(sendSmtpEmail);
        } catch (ApiException e) {
            System.out.println("Status code: " + e.getCode());
            System.out.println("Response body: " + e.getResponseBody());
            throw new RuntimeException("Erro ao enviar email: " + e.getResponseBody());
        }
    }
}

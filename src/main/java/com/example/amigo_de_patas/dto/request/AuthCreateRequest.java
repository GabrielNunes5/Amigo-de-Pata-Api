package com.example.amigo_de_patas.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthCreateRequest {

    @NotBlank(message = "Email é obrigatório")
    @Email
    private String adopterEmail;

    @NotBlank(message = "Senha é obrigatória")
    private String adopterPassword;
}

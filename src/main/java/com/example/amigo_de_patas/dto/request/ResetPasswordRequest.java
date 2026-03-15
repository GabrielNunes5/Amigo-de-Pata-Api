package com.example.amigo_de_patas.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "A senha é obrigatório")
    private String adopterPassword;

    @NotBlank(message = "A confirmação de senha é obrigatório")
    private String adopterConfirmPassword;
}

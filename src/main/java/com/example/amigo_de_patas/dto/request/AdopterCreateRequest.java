package com.example.amigo_de_patas.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdopterCreateRequest {
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
    private String adopterFullName;

    @Past(message = "A data de nascimento deve estar no passado")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate adopterBirthDate;

    @NotBlank(message = "CPF é obrigatório")
    @CPF
    private String adopterCPF;

    @NotBlank(message = "Email é obrigatório")
    @Email
    private String adopterEmail;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "A senha deve conter pelo menos: uma letra maiúscula, uma letra minúscula, um número e um caractere especial"
    )
    private String adopterPassword;

    @Pattern(
            regexp = "^\\d{2}9?\\d{8}$",
            message = "Telefone inválido. Formato esperado: DDD + número. Ex: 11987654321"
    )
    @NotBlank(message = "Numero de telefone é obrigatório")
    private String adopterPhone;

    @NotBlank(message = "Endereço é obrigatório")
    private String adopterAddress;

    @NotBlank(message = "O tipo da casa é obrigatório")
    private String typeHouse;

    @NotNull
    private Boolean hasGarden = false;
}

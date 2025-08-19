package com.example.amigo_de_patas.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate adopterBirthDate;

    @NotBlank(message = "CPF é obrigatório")
    @CPF
    private String adopterCPF;

    @NotBlank(message = "Email é obrigatório")
    @Email
    private String adopterEmail;

    @NotBlank(message = "Numero de telefone é obrigatório")
    private String adopterPhone;

    @NotBlank(message = "Endereço é obrigatório")
    private String adopterAddress;
}

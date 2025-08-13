package com.example.amigo_de_patas.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdopterUpdateRequest {

    @Size(max = 150, message = "Name must be up to 100 characters")
    private String adopterFullName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate adopterBirthDate;

    @CPF
    private String adopterCPF;

    @Email
    private String adopterEmail;

    private String adopterPhone;

    private String adopterAddress;
}

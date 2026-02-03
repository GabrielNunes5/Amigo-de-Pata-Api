package com.example.amigo_de_patas.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoluntaryCreateRequest {
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String voluntaryName;

    @NotBlank(message = "Email é obrigatório")
    @Email
    private String voluntaryEmail;

    @Pattern(
            regexp = "^\\d{2}9?\\d{8}$",
            message = "Telefone inválido. Formato esperado: DDD + número. Ex: 11987654321"
    )
    @NotBlank(message = "Numero de telefone é obrigatório")
    private String voluntaryPhone;

    @Past(message = "A data de nascimento deve estar no passado")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate voluntaryBirthDate;

    @Size(max = 30, message = "A profissão deve ter no máximo 30 caracteres")
    private String voluntaryOccupation;

    @NotBlank(message = "Area de interesse é obrigatório")
    private String voluntaryInterestArea;

    @NotBlank(message = "Disponibilidade é obrigatório")
    private String voluntaryAvailability;

    private String voluntaryExperience;

    private String voluntaryMotivation;

    @NotNull
    private Boolean hasTransportation = false;

    @NotNull
    private Boolean hasRescueExperience = false;

    @NotNull
    private Boolean canWorkOnWeekends = false;

    private String voluntarySpecialSkills;
}

package com.example.amigo_de_patas.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCreateRequest {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 150 caracteres")
    private String animalName;

    @NotBlank(message = "A idade é obrigatório")
    @Size(max = 50, message = "A idade deve ter no máximo 50 caracteres")
    private String animalAge;

    @NotNull(message = "O peso obrigatório")
    @Positive(message = "O peso deve ser positivo")
    private BigDecimal animalWeight;

    @NotBlank(message = "O sexo é obrigatório")
    @Size(max = 10, message = "O sexo deve ter no máximo 10 caracteres")
    private String animalSex;

    @Size(max = 50, message = "A cor deve ter no máximo 50 caracteres")
    private String animalColor;

    @Size(max = 50, message = "A espécie deve ter no máximo 50 caracteres")
    private String animalSpecies;

    @Size(max = 255, message = "As vacinas devem ter no máximo 255 caracteres")
    private String animalVaccines;

    @Size(max = 50, message = "O tamanho deve ter no máximo 50 caracteres")
    private String animalSize;

    private Boolean animalNeutered;

    @Size(max = 255, message = "A condições devem ter no máximo 255 caracteres")
    private String animalSpecialConditions;

    @NotBlank(message = "A imagem é obrigatória")
    @Size(max = 255, message = "A URL da imagem deve ter no máximo 255 caracteres")
    private String animalImageUrl;
}
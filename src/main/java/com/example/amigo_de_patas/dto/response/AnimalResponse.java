package com.example.amigo_de_patas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalResponse {
    private UUID animalId;
    private String animalName;
    private String animalAge;
    private BigDecimal animalWeight;
    private String animalNumAge;
    private String animalSex;
    private String animalColor;
    private String animalSpecies;
    private String animalVaccines;
    private String animalSize;
    private Boolean animalNeutered;
    private String animalSpecialConditions;
    private String animalCategory;
    private String animalImageUrl;
    private Boolean animalAdopted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

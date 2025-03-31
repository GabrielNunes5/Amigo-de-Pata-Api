package com.example.amigo_de_patas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalsResponse {
    private UUID id;
    private String animalName;
    private String animalAge;
    private Double animalWeight;
    private String animalSex;
    private String animalColor;
    private String animalSpecies;
    private String animalVaccines;
    private String animalSized;
    private Boolean animalNeutered;
    private String animalSpecialConditions;
    private String animalCategory;
    private String animalImageUrl;
    private Boolean animalAdopted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

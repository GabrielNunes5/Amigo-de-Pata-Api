package com.example.amigo_de_patas.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalsUpdateRequest {

    @Size(max = 100, message = "Name must be up to 100 characters")
    private String animalName;

    @Size(max = 50, message = "Age description too long")
    private String animalAge;

    @Positive(message = "Weight must be a positive value")
    private Double animalWeight;

    @Size(max = 10, message = "Sex must be up to 10 characters")
    private String animalSex;

    @Size(max = 50, message = "Color too long")
    private String animalColor;

    @Size(max = 50, message = "Species too long")
    private String animalSpecies;

    @Size(max = 255, message = "Vaccines info too long (max 255 characters)")
    private String animalVaccines;

    @Size(max = 50, message = "Size too long")
    private String animalSized;

    private Boolean animalNeutered;

    @Size(max = 255, message = "Special conditions info too long")
    private String animalSpecialConditions;

    @Size(max = 100, message = "Category too long")
    private String animalCategory;

    @Size(max = 255, message = "Image URL too long")
    private String animalImageUrl;

    private Boolean animalAdopted;
}
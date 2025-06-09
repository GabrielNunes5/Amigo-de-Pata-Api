package com.example.amigo_de_patas.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCreateRequest {

    @NotBlank(message = "Animal name is mandatory")
    @Size(max = 100, message = "Name must be up to 100 characters")
    private String animalName;

    @NotBlank(message = "Animal age is mandatory")
    @Size(max = 50, message = "Age description too long")
    private String animalAge;

    @NotNull(message = "Weight is mandatory")
    @Positive(message = "Weight must be positive")
    private BigDecimal animalWeight;

    @NotBlank(message = "Animal number age is mandatory")
    @Size(max = 100, message = "Animal number age too long")
    private String animalNumAge;

    @NotBlank(message = "Sex is mandatory")
    @Size(max = 10, message = "Sex must be up to 10 characters")
    private String animalSex;

    @Size(max = 50, message = "Color too long")
    private String animalColor;

    @Size(max = 50, message = "Species too long")
    private String animalSpecies;

    @Size(max = 255, message = "Vaccines info too long")
    private String animalVaccines;

    @Size(max = 50, message = "Size too long")
    private String animalSize;

    private Boolean animalNeutered;

    @Size(max = 255, message = "Conditions info too long")
    private String animalSpecialConditions;

    @NotBlank(message = "Category is mandatory")
    @Size(max = 100, message = "Category too long")
    private String animalCategory;

    @NotBlank(message = "Image URL is mandatory")
    @Size(max = 255, message = "URL too long")
    private String animalImageUrl;
}
package com.example.amigo_de_patas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionFormResponse {

    private UUID formId;

    private UUID adopterId;
    private String adopterName;

    private UUID animalId;
    private String animalName;

    private String experience;
    private String otherAnimals;
    private String message;
    private String createdAt;

}

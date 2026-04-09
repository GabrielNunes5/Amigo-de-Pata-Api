package com.example.amigo_de_patas.dto.response;

import com.example.amigo_de_patas.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdopterAdoptionFormResponse {

    private UUID adoptionFormId;
    private UUID animalId;
    private String animalName;
    private String experience;
    private String otherAnimals;
    private String message;
    private Status status;
    private LocalDateTime createdAt;
}

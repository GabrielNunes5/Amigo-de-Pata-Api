package com.example.amigo_de_patas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptedAnimalResponse {
    private UUID animalId;
    private String animalName;
    private String animalSpecies;
    private String animalAge;
    private Boolean animalAdopted;
    private List<String> animalImages;
}

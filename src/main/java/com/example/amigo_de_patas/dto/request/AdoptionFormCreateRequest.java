package com.example.amigo_de_patas.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionFormCreateRequest {

    @NotNull
    @JsonProperty("adopter_id")
    private UUID adopterId;

    @NotNull
    @JsonProperty("animal_id")
    private UUID animalId;

    @Size(max = 255, message = "A experiencia devem ter no máximo 255 caracteres")
    private String experience;

    @Size(max = 255, message = "Outros animais devem ter no máximo 255 caracteres")
    private String otherAnimals;

    @Size(max = 255, message = "A mensagem devem ter no máximo 255 caracteres")
    private String message;

}

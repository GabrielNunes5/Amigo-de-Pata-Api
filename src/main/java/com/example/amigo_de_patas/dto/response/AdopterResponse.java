package com.example.amigo_de_patas.dto.response;

import com.example.amigo_de_patas.model.Animal;
import com.example.amigo_de_patas.model.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdopterResponse {
    private UUID adopterId;
    private String adopterFullName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate adopterBirthDate;
    private String adopterCPF;
    private String adopterEmail;
    private String adopterPhone;
    private String adopterAddress;
    private String typeHouse;
    private Boolean hasGarden;
    private Role role;
    private List<Animal> adoptedAnimals;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}

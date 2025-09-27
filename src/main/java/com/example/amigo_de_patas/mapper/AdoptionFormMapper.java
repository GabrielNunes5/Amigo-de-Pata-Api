package com.example.amigo_de_patas.mapper;

import com.example.amigo_de_patas.dto.request.AdoptionFormCreateRequest;
import com.example.amigo_de_patas.dto.response.AdoptionFormResponse;
import com.example.amigo_de_patas.model.Adopter;
import com.example.amigo_de_patas.model.AdoptionForm;
import com.example.amigo_de_patas.model.Animal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdoptionFormMapper {

    @Mapping(target = "adopter", source = "adopter")
    @Mapping(target = "animal", source = "animal")
    @Mapping(target = "adoptionFormId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AdoptionForm toEntity(AdoptionFormCreateRequest dto, Adopter adopter, Animal animal);

    @Mapping(target = "formId", source = "adoptionFormId")
    @Mapping(target = "adopterId", source = "adopter.adopterId")
    @Mapping(target = "adopterName", source = "adopter.adopterFullName")
    @Mapping(target = "animalId", source = "animal.animalId")
    @Mapping(target = "animalName", source = "animal.animalName")
    @Mapping(target = "createdAt", source = "createdAt")
    AdoptionFormResponse toResponse(AdoptionForm entity);
}

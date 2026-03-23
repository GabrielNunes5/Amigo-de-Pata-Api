package com.example.amigo_de_patas.mapper;

import com.example.amigo_de_patas.dto.request.AdopterCreateRequest;
import com.example.amigo_de_patas.dto.request.AdopterUpdateRequest;
import com.example.amigo_de_patas.dto.response.AdoptedAnimalResponse;
import com.example.amigo_de_patas.dto.response.AdopterResponse;
import com.example.amigo_de_patas.model.Adopter;
import com.example.amigo_de_patas.model.Animal;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AdopterMapper {
    Adopter toEntity(AdopterCreateRequest dto);

    @Mapping(target = "adoptedAnimals", source = "adoptedAnimals")
    AdopterResponse toResponse(Adopter entity);

    @Mapping(target = "animalImages", expression = "java(animal.getAnimalImages().stream().map(img -> img.getUrl()).collect(java.util.stream.Collectors.toList()))")
    AdoptedAnimalResponse toAdoptedAnimalResponse(Animal animal);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAdopterFromDto(AdopterUpdateRequest dto, @MappingTarget Adopter entity);
}

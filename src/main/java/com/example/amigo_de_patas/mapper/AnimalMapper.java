package com.example.amigo_de_patas.mapper;

import com.example.amigo_de_patas.dto.request.AnimalCreateRequest;
import com.example.amigo_de_patas.model.Animal;
import com.example.amigo_de_patas.dto.request.AnimalUpdateRequest;
import com.example.amigo_de_patas.dto.response.AnimalResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AnimalMapper {
    Animal toEntity(AnimalCreateRequest dto);

    AnimalResponse toResponse(Animal entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAnimalFromDto(AnimalUpdateRequest dto, @MappingTarget Animal entity);
}

package com.example.amigo_de_patas.mapper;

import com.example.amigo_de_patas.dto.request.AnimalCreateRequest;
import com.example.amigo_de_patas.model.Animal;
import com.example.amigo_de_patas.dto.request.AnimalUpdateRequest;
import com.example.amigo_de_patas.dto.response.AnimalResponse;
import com.example.amigo_de_patas.model.AnimalImage;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    Animal toEntity(AnimalCreateRequest dto);

    @Mapping(target = "animalImages", source = "animalImages")
    AnimalResponse toResponse(Animal entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAnimalFromDto(AnimalUpdateRequest dto, @MappingTarget Animal entity);

    default List<String> map(List<AnimalImage> images) {
        if (images == null) return List.of();
        return images.stream()
                .map(AnimalImage::getUrl)
                .toList();
    }
}


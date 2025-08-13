package com.example.amigo_de_patas.mapper;

import com.example.amigo_de_patas.dto.request.AdopterCreateRequest;
import com.example.amigo_de_patas.dto.request.AdopterUpdateRequest;
import com.example.amigo_de_patas.dto.response.AdopterResponse;
import com.example.amigo_de_patas.model.Adopter;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AdopterMapper {
    Adopter toEntity(AdopterCreateRequest dto);

    AdopterResponse toResponse(Adopter entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAdopterFromDto(AdopterUpdateRequest dto, @MappingTarget Adopter entity);
}

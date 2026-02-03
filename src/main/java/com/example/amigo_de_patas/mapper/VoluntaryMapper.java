package com.example.amigo_de_patas.mapper;

import com.example.amigo_de_patas.dto.request.VoluntaryCreateRequest;
import com.example.amigo_de_patas.dto.response.VoluntaryResponse;
import com.example.amigo_de_patas.model.Voluntary;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VoluntaryMapper {
    Voluntary toEntity(VoluntaryCreateRequest dto);

    VoluntaryResponse toResponse(Voluntary entity);

    default String map(List<String> value) {
        return value == null || value.isEmpty()
                ? null
                : String.join(",", value);
    }

    default List<String> map(String value) {
        return value == null || value.isBlank()
                ? List.of()
                : Arrays.asList(value.split(","));
    }
}

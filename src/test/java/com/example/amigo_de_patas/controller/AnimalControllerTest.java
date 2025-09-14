package com.example.amigo_de_patas.controller;

import com.example.amigo_de_patas.dto.request.AnimalCreateRequest;
import com.example.amigo_de_patas.dto.response.AdopterResponse;
import com.example.amigo_de_patas.dto.response.AnimalResponse;
import com.example.amigo_de_patas.model.Animal;
import com.example.amigo_de_patas.service.AnimalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnimalController.class)
class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    @Autowired
    private ObjectMapper objectMapper;

    private Animal animal;
    private AnimalCreateRequest createRequest;
    private AnimalResponse response;

    @BeforeEach
    void setUp() {
        UUID id = UUID.randomUUID();
        animal = new Animal();
        animal.setAnimalId(id);
        animal.setAnimalName("Bidu");

        createRequest = new AnimalCreateRequest();
        createRequest.setAnimalName("Bidu");
        createRequest.setAnimalAge("Jovem");
        createRequest.setAnimalWeight(BigDecimal.valueOf(2.0));
        createRequest.setAnimalSex("Macho");
        createRequest.setAnimalSpecies("Cachorro");
        createRequest.setAnimalImageUrl("http://example.com/images/bidu.jpg");

        response = new AnimalResponse();
        response.setAnimalId(id);
        response.setAnimalName("Bidu");
        response.setAnimalAge("Jovem");
        response.setAnimalWeight(BigDecimal.valueOf(2.0));
        response.setAnimalSex("Macho");
        response.setAnimalImageUrl("http://example.com/images/bidu.jpg");
    }

    @Test
    void shouldCreateAnimal() throws Exception {
        when(animalService.createAnimal(any())).thenReturn(response);

        mockMvc.perform(post("/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.animalName").value("Bidu"));
    }

    @Test
    void shouldReturnAllAnimals() throws Exception {
        Page<AnimalResponse> page = new PageImpl<>(List.of(response));
        when(animalService.getAllAnimals(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/animals")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content", hasSize(1)))
                .andExpect(jsonPath("$.data.content[0].animalName").value("Bidu"));
    }

    @Test
    void shouldReturnAnimalById() throws Exception {
        UUID id = response.getAnimalId();
        when(animalService.getAnimalById(id)).thenReturn(response);

        mockMvc.perform(get("/animals/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalName").value("Bidu"));
    }
}

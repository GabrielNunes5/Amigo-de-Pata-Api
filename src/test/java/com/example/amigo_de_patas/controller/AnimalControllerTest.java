package com.example.amigo_de_patas.controller;

import com.example.amigo_de_patas.dto.request.AnimalCreateRequest;
import com.example.amigo_de_patas.service.AnimalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnimalController.class)
class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {
        @Bean
        @Primary
        AnimalService animalService() {
            return mock(AnimalService.class);
        }
    }

    @Test
    @DisplayName("POST /animals - Should create a new animal")
    void shouldCreateAnimalSuccessfully() throws Exception {
        // Arrange
        AnimalCreateRequest request = new AnimalCreateRequest();
        request.setAnimalName("Bidu");
        request.setAnimalAge("Adult");
        request.setAnimalNumAge("3");
        request.setAnimalWeight(BigDecimal.valueOf(12.5));
        request.setAnimalSex("Male");
        request.setAnimalCategory("Dog");
        request.setAnimalImageUrl("http://example.com/bidu.jpg");

        when(animalService.createAnimal(Mockito.any()));

        // Act & Assert
        mockMvc.perform(post("/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalId", is(1)))
                .andExpect(jsonPath("$.animalName", is("Bidu")))
                .andExpect(jsonPath("$.animalAge", is("Adult")))
                .andExpect(jsonPath("$.animalNumAge", is("3")))
                .andExpect(jsonPath("$.animalWeight", is(12.5)))
                .andExpect(jsonPath("$.animalSex", is("Male")))
                .andExpect(jsonPath("$.animalCategory", is("Dog")))
                .andExpect(jsonPath("$.animalImageUrl", is("http://example.com/bidu.jpg")))
                .andExpect(jsonPath("$.adopted", is(true)));
    }
}
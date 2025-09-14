package com.example.amigo_de_patas.controller;

import com.example.amigo_de_patas.dto.request.AdopterCreateRequest;
import com.example.amigo_de_patas.dto.response.AdopterResponse;
import com.example.amigo_de_patas.model.Adopter;
import com.example.amigo_de_patas.service.AdopterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdopterController.class)
public class AdopterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdopterService adopterService;

    @Autowired
    private ObjectMapper objectMapper;

    private Adopter adopter;
    private AdopterCreateRequest createRequest;
    private AdopterResponse response;

    @BeforeEach
    void setUp(){
        UUID id = UUID.randomUUID();
        adopter = new Adopter();
        adopter.setAdopterId(id);
        adopter.setAdopterFullName("Jhon Doe");

        createRequest = new AdopterCreateRequest();
        createRequest.setAdopterFullName("Jhon Doe");
        createRequest.setAdopterCPF("61548851302");
        createRequest.setAdopterAddress("Rua A, 10, Cidade X");
        createRequest.setAdopterEmail("jhon@gmail.com");
        createRequest.setAdopterPhone("5555555555");
        createRequest.setAdopterBirthDate(LocalDate.of(1990, 1, 1));

        response = new AdopterResponse();
        response.setAdopterId(id);
        response.setAdopterFullName("Jhon Doe");
        response.setAdopterCPF("61548851302");
        response.setAdopterAddress("Rua A, 10, Cidade X");
        response.setAdopterEmail("jhon@gmail.com");
        response.setAdopterPhone("5555555555");
        response.setAdopterBirthDate(LocalDate.of(1990, 1, 1));
    }

    @Test
    void shouldCreateAdopter() throws Exception {
        when(adopterService.createAdopter(any())).thenReturn(response);

        mockMvc.perform(post("/adopters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.adopterFullName").value("Jhon Doe"))
                .andExpect(jsonPath("$.adopterCPF").value("61548851302"));
    }

    @Test
    void shouldReturnAllAdopters() throws Exception {
        Page<AdopterResponse> page = new PageImpl<>(List.of(response));

        when(adopterService.getAllAdopters(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/adopters")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content", hasSize(1)))
                .andExpect(jsonPath("$.data.content[0].adopterFullName").value("Jhon Doe"));
    }



    @Test
    void shouldReturnAdopterById() throws Exception {
        UUID id = response.getAdopterId();
        when(adopterService.getAdopterById(id)).thenReturn(response);

        mockMvc.perform(get("/adopters/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adopterFullName").value("Jhon Doe"));
    }
}

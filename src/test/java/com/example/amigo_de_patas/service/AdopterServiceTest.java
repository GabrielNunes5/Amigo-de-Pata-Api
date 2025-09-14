package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.dto.request.AdopterCreateRequest;
import com.example.amigo_de_patas.dto.request.AdopterUpdateRequest;
import com.example.amigo_de_patas.dto.response.AdopterResponse;
import com.example.amigo_de_patas.exceptions.ResourceNotFoundException;
import com.example.amigo_de_patas.mapper.AdopterMapper;
import com.example.amigo_de_patas.model.Adopter;
import com.example.amigo_de_patas.repository.AdopterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdopterServiceTest {

    @InjectMocks
    private AdopterService adopterService;

    @Mock
    private AdopterRepository adopterRepository;

    @Mock
    private AdopterMapper adopterMapper;

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
    void shouldCreateAdopter(){
        when(adopterMapper.toEntity(createRequest)).thenReturn(adopter);
        when(adopterRepository.save(adopter)).thenReturn(adopter);
        when(adopterMapper.toResponse(adopter)).thenReturn(response);

        AdopterResponse result = adopterService.createAdopter(createRequest);

        assertNotNull(result);
        assertEquals("Jhon Doe", result.getAdopterFullName());
        verify(adopterRepository).save(adopter);
    }

    @Test
    void shouldReturnPagedAdopters() {
        Page<Adopter> adoptersPage = new PageImpl<>(List.of(adopter));

        when(adopterRepository.findAll(any(Pageable.class))).thenReturn(adoptersPage);
        when(adopterMapper.toResponse(adopter)).thenReturn(response);

        Page<AdopterResponse> result = adopterService.getAllAdopters(PageRequest.of(0, 10));

        assertEquals(1, result.getContent().size());
        assertEquals(response, result.getContent().getFirst());
        verify(adopterRepository).findAll(any(Pageable.class));
    }



    @Test
    void shouldReturnAdopterById() {
        when(adopterRepository.findById(adopter.getAdopterId())).thenReturn(Optional.of(adopter));
        when(adopterMapper.toResponse(adopter)).thenReturn(response);

        AdopterResponse result = adopterService.getAdopterById(adopter.getAdopterId());

        assertEquals("Jhon Doe", result.getAdopterFullName());
    }

    @Test
    void shouldThrowExceptionWhenAdopterNotFound() {
        UUID id = UUID.randomUUID();
        when(adopterRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            adopterService.getAdopterById(id);
        });
    }

    @Test
    void shouldUpdateAdopter() {
        AdopterUpdateRequest updateRequest = new AdopterUpdateRequest();
        updateRequest.setAdopterFullName("Jhon Smith");

        when(adopterRepository.findById(adopter.getAdopterId())).thenReturn(Optional.of(adopter));
        doAnswer(inv -> {
            adopter.setAdopterFullName(updateRequest.getAdopterFullName());
            return null;
        }).when(adopterMapper).updateAdopterFromDto(updateRequest, adopter);
        when(adopterRepository.save(adopter)).thenReturn(adopter);
        when(adopterMapper.toResponse(adopter)).thenReturn(response);

        AdopterResponse result = adopterService.updateAdopter(adopter.getAdopterId(), updateRequest);

        assertNotNull(result);
        verify(adopterRepository).save(adopter);
    }

    @Test
    void shouldDeleteAdopterWhenExists() {
        when(adopterRepository.existsById(adopter.getAdopterId())).thenReturn(true);

        adopterService.deleteAdopter(adopter.getAdopterId());

        verify(adopterRepository).deleteById(adopter.getAdopterId());
    }

    @Test
    void shouldThrowWhenDeletingNonExistingAdopter() {
        UUID id = UUID.randomUUID();
        when(adopterRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            adopterService.deleteAdopter(id);
        });
    }
}

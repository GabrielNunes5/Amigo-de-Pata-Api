package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.dto.request.AnimalCreateRequest;
import com.example.amigo_de_patas.dto.request.AnimalUpdateRequest;
import com.example.amigo_de_patas.dto.response.AnimalResponse;
import com.example.amigo_de_patas.exceptions.ResourceNotFoundException;
import com.example.amigo_de_patas.mapper.AnimalMapper;
import com.example.amigo_de_patas.model.Animal;
import com.example.amigo_de_patas.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @InjectMocks
    private AnimalService animalService;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AnimalMapper animalMapper;

    private Animal animal;
    private AnimalCreateRequest createRequest;
    private AnimalResponse animalResponse;

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
        createRequest.setAnimalNumAge("1 Ano");
        createRequest.setAnimalSex("Male");
        createRequest.setAnimalCategory("Dog");
        createRequest.setAnimalImageUrl("http://example.com/images/bidu.jpg");

        animalResponse = new AnimalResponse();
        animalResponse.setAnimalId(id);
        animalResponse.setAnimalName("Bidu");
        animalResponse.setAnimalAge("Jovem");
        animalResponse.setAnimalWeight(BigDecimal.valueOf(2.0));
        animalResponse.setAnimalNumAge("1 Ano");
        animalResponse.setAnimalSex("Male");
        animalResponse.setAnimalCategory("Dog");
        animalResponse.setAnimalImageUrl("http://example.com/images/bidu.jpg");
    }

    @Test
    void shouldCreateAnimal() {
        when(animalMapper.toEntity(createRequest)).thenReturn(animal);
        when(animalRepository.save(animal)).thenReturn(animal);
        when(animalMapper.toResponse(animal)).thenReturn(animalResponse);

        AnimalResponse result = animalService.createAnimal(createRequest);

        assertNotNull(result);
        assertEquals("Bidu", result.getAnimalName());
        verify(animalRepository).save(animal);
    }

    @Test
    void shouldReturnAllAnimals() {
        List<Animal> animals = List.of(animal);
        List<AnimalResponse> responses = List.of(animalResponse);

        when(animalRepository.findAll()).thenReturn(animals);
        when(animalMapper.toResponse(animal)).thenReturn(animalResponse);

        List<AnimalResponse> result = animalService.getAllAnimals();

        assertEquals(1, result.size());
        verify(animalRepository).findAll();
    }

    @Test
    void shouldReturnAnimalById() {
        when(animalRepository.findById(animal.getAnimalId())).thenReturn(Optional.of(animal));
        when(animalMapper.toResponse(animal)).thenReturn(animalResponse);

        AnimalResponse result = animalService.getAnimalById(animal.getAnimalId());

        assertEquals("Bidu", result.getAnimalName());
    }

    @Test
    void shouldThrowExceptionWhenAnimalNotFound() {
        UUID id = UUID.randomUUID();
        when(animalRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            animalService.getAnimalById(id);
        });
    }

    @Test
    void shouldUpdateAnimal() {
        AnimalUpdateRequest updateRequest = new AnimalUpdateRequest();
        updateRequest.setAnimalName("Rex");

        when(animalRepository.findById(animal.getAnimalId())).thenReturn(Optional.of(animal));
        doAnswer(inv -> {
            animal.setAnimalName(updateRequest.getAnimalName());
            return null;
        }).when(animalMapper).updateAnimalFromDto(updateRequest, animal);
        when(animalRepository.save(animal)).thenReturn(animal);
        when(animalMapper.toResponse(animal)).thenReturn(animalResponse);

        AnimalResponse result = animalService.updateAnimal(animal.getAnimalId(), updateRequest);

        assertNotNull(result);
        verify(animalRepository).save(animal);
    }

    @Test
    void shouldDeleteAnimalWhenExists() {
        when(animalRepository.existsById(animal.getAnimalId())).thenReturn(true);

        animalService.deleteAnimal(animal.getAnimalId());

        verify(animalRepository).deleteById(animal.getAnimalId());
    }

    @Test
    void shouldThrowWhenDeletingNonExistingAnimal() {
        UUID id = UUID.randomUUID();
        when(animalRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            animalService.deleteAnimal(id);
        });
    }

}

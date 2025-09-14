package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.dto.request.AnimalCreateRequest;
import com.example.amigo_de_patas.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.amigo_de_patas.repository.AnimalRepository;
import com.example.amigo_de_patas.model.Animal;
import com.example.amigo_de_patas.dto.request.AnimalUpdateRequest;
import com.example.amigo_de_patas.dto.response.AnimalResponse;
import com.example.amigo_de_patas.mapper.AnimalMapper;
import java.util.UUID;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    public AnimalService(AnimalRepository animalRepository, AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
    }

    @Transactional
    public AnimalResponse createAnimal(AnimalCreateRequest req) {
        Animal entity = animalMapper.toEntity(req);
        Animal saved = animalRepository.save(entity);
        return animalMapper.toResponse(saved);
    }


    public Page<AnimalResponse> getAllAnimals(Pageable pageable) {
        return animalRepository.findAll(pageable)
                .map(animalMapper::toResponse);
    }

    public AnimalResponse getAnimalById(UUID id) {
        Animal entity = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));
        return animalMapper.toResponse(entity);
    }

    @Transactional
    public AnimalResponse updateAnimal(UUID id, AnimalUpdateRequest req) {
        Animal entity = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));
        animalMapper.updateAnimalFromDto(req, entity);
        Animal updated = animalRepository.save(entity);
        return animalMapper.toResponse(updated);
    }

    @Transactional
    public void deleteAnimal(UUID id) {
        if (!animalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Animal não encontrado");
        }
        animalRepository.deleteById(id);
    }
}

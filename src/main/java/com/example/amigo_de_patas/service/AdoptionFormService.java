package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.dto.request.AdoptionFormCreateRequest;
import com.example.amigo_de_patas.dto.response.AdoptionFormResponse;
import com.example.amigo_de_patas.exceptions.ResourceNotFoundException;
import com.example.amigo_de_patas.mapper.AdoptionFormMapper;
import com.example.amigo_de_patas.model.Adopter;
import com.example.amigo_de_patas.model.AdoptionForm;
import com.example.amigo_de_patas.model.Animal;
import com.example.amigo_de_patas.repository.AdopterRepository;
import com.example.amigo_de_patas.repository.AdoptionFormRepository;
import com.example.amigo_de_patas.repository.AnimalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AdoptionFormService {
    private final AdoptionFormRepository adoptionFormRepository;
    private final AdoptionFormMapper adoptionFormMapper;
    private final AdopterRepository adopterRepository;
    private final AnimalRepository animalRepository;

    public AdoptionFormService(AdoptionFormRepository adoptionFormRepository,
                               AdoptionFormMapper adoptionFormMapper,
                               AdopterRepository adopterRepository,
                               AnimalRepository animalRepository) {
        this.adoptionFormRepository = adoptionFormRepository;
        this.adoptionFormMapper = adoptionFormMapper;
        this.adopterRepository = adopterRepository;
        this.animalRepository = animalRepository;
    }

    @Transactional
    public AdoptionFormResponse createAdoptionForm(AdoptionFormCreateRequest req, UUID adopterId){
        Adopter adopter = adopterRepository.findById(adopterId)
                .orElseThrow(() -> new ResourceNotFoundException("Adotante não encontrado"));

        Animal animal = animalRepository.findById(req.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));

        AdoptionForm entity = adoptionFormMapper.toEntity(req, adopter, animal);
        AdoptionForm saved = adoptionFormRepository.save(entity);
        return adoptionFormMapper.toResponse(saved);
    }

    public Page<AdoptionFormResponse> findAll(
            UUID animalId,
            UUID adopterId,
            Pageable pageable
    ) {
        if (animalId != null) {
            return adoptionFormRepository
                    .findAllByAnimal_AnimalId(animalId, pageable)
                    .map(adoptionFormMapper::toResponse);
        }
    
        if (adopterId != null) {
            return adoptionFormRepository
                    .findAllByAdopter_AdopterId(adopterId, pageable)
                    .map(adoptionFormMapper::toResponse);
        }
    
        return adoptionFormRepository
                .findAll(pageable)
                .map(adoptionFormMapper::toResponse);
    }

    public AdoptionFormResponse getAdoptionFormById(UUID id){
        AdoptionForm entity = adoptionFormRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formulario Não encontrado"));
        return adoptionFormMapper.toResponse(entity);
    }

    public Page<AdoptionFormResponse> getAdoptionFormByAdopterId(UUID adopterId, Pageable pageable){
        if(!adopterRepository.existsById(adopterId)){
            throw new ResourceNotFoundException("Adotante não encontrado");
        }
        return adoptionFormRepository.findAllByAdopter_AdopterId(adopterId, pageable)
                .map(adoptionFormMapper::toResponse);
    }

    public Page<AdoptionFormResponse> getAdoptionFormByAnimalId(UUID animalId, Pageable pageable){
        if(!animalRepository.existsById(animalId)){
            throw new ResourceNotFoundException("Animal não encontrado");
        }
        return adoptionFormRepository.findAllByAnimal_AnimalId(animalId, pageable)
                .map(adoptionFormMapper::toResponse);
    }
}


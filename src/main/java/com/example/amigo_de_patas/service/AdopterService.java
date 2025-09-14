package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.dto.request.AdopterCreateRequest;
import com.example.amigo_de_patas.dto.request.AdopterUpdateRequest;
import com.example.amigo_de_patas.dto.response.AdopterResponse;
import com.example.amigo_de_patas.exceptions.ResourceNotFoundException;
import com.example.amigo_de_patas.mapper.AdopterMapper;
import com.example.amigo_de_patas.model.Adopter;
import com.example.amigo_de_patas.repository.AdopterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdopterService {
    private final AdopterRepository adopterRepository;
    private final AdopterMapper adopterMapper;

    public AdopterService(AdopterRepository adopterRepository, AdopterMapper adopterMapper) {
        this.adopterRepository = adopterRepository;
        this.adopterMapper = adopterMapper;
    }

    @Transactional
    public AdopterResponse createAdopter(AdopterCreateRequest req){
        Adopter entity = adopterMapper.toEntity(req);
        Adopter saved = adopterRepository.save(entity);
        return adopterMapper.toResponse(saved);
    }

    public Page<AdopterResponse> getAllAdopters(Pageable pageable){
        return adopterRepository.findAll(pageable)
                .map(adopterMapper::toResponse);
    }

    public AdopterResponse getAdopterById(UUID id){
        Adopter entity = adopterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adotante não encontrado"));
        return adopterMapper.toResponse(entity);
    }

    @Transactional
    public AdopterResponse updateAdopter(UUID id, AdopterUpdateRequest req){
        Adopter entity = adopterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adotante não encontrado"));
        adopterMapper.updateAdopterFromDto(req, entity);
        Adopter updated = adopterRepository.save(entity);
        return adopterMapper.toResponse(updated);
    }

    @Transactional
    public void deleteAdopter(UUID id){
        if (!adopterRepository.existsById(id)){
            throw new ResourceNotFoundException("Adotante não encontrado");
        }
        adopterRepository.deleteById(id);
    }
}

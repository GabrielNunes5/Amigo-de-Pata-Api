package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.dto.request.AdopterCreateRequest;
import com.example.amigo_de_patas.dto.request.AdopterUpdateRequest;
import com.example.amigo_de_patas.dto.response.AdopterResponse;
import com.example.amigo_de_patas.exceptions.BadRequestException;
import com.example.amigo_de_patas.exceptions.ConflictException;
import com.example.amigo_de_patas.exceptions.ResourceNotFoundException;
import com.example.amigo_de_patas.mapper.AdopterMapper;
import com.example.amigo_de_patas.model.Adopter;
import com.example.amigo_de_patas.repository.AdopterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Service
public class AdopterService {
    private final AdopterRepository adopterRepository;
    private final AdopterMapper adopterMapper;

    private boolean isAdult(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

    public AdopterService(AdopterRepository adopterRepository, AdopterMapper adopterMapper) {
        this.adopterRepository = adopterRepository;
        this.adopterMapper = adopterMapper;
    }

    @Transactional
    public AdopterResponse createAdopter(AdopterCreateRequest req){
        if (!isAdult(req.getAdopterBirthDate())) {
            throw new BadRequestException("O adotante deve ter pelo menos 18 anos");
        }

        if (adopterRepository.findAdopterByAdopterEmail(req.getAdopterEmail()).isPresent()) {
            throw new ConflictException("Email já cadastrado");
        }

        if (adopterRepository.findAdopterByAdopterCPF(req.getAdopterCPF()).isPresent()) {
            throw new ConflictException("CPF já cadastrado");
        }

        if (adopterRepository.findAdopterByAdopterPhone(req.getAdopterPhone()).isPresent()){
            throw new ConflictException("Numero de Telefone ja cadastrado");
        }

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

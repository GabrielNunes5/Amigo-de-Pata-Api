package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.dto.request.VoluntaryCreateRequest;
import com.example.amigo_de_patas.dto.response.VoluntaryResponse;
import com.example.amigo_de_patas.exceptions.BadRequestException;
import com.example.amigo_de_patas.exceptions.ConflictException;
import com.example.amigo_de_patas.exceptions.ResourceNotFoundException;
import com.example.amigo_de_patas.mapper.VoluntaryMapper;
import com.example.amigo_de_patas.model.Voluntary;
import com.example.amigo_de_patas.repository.VoluntaryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Service
public class VoluntaryService {
    private final VoluntaryRepository voluntaryRepository;
    private final VoluntaryMapper voluntaryMapper;

    private boolean isAdult(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

    public VoluntaryService(VoluntaryRepository voluntaryRepository, VoluntaryMapper voluntaryMapper) {
        this.voluntaryRepository = voluntaryRepository;
        this.voluntaryMapper = voluntaryMapper;
    }

    @Transactional
    public VoluntaryResponse createVoluntary(VoluntaryCreateRequest req) {
        if(!isAdult(req.getVoluntaryBirthDate())){
            throw new BadRequestException("O Voluntario deve ter pelo menos 18 anos");
        }

        if (voluntaryRepository.findVoluntaryByVoluntaryEmail(req.getVoluntaryEmail()).isPresent()){
            throw new ConflictException("Email ja cadastrado");
        }

        if (voluntaryRepository.findVoluntaryByVoluntaryPhone(req.getVoluntaryPhone()).isPresent()){
            throw new ConflictException("Numero de telefone ja cadastrado");
        }

        Voluntary entity = voluntaryMapper.toEntity(req);
        Voluntary saved = voluntaryRepository.save(entity);
        return voluntaryMapper.toResponse(saved);
    }

    public Page<VoluntaryResponse> getAllVoluntaries(Pageable pageable){
        return voluntaryRepository.findAll(pageable)
                .map(voluntaryMapper::toResponse);
    }

    public VoluntaryResponse getVoluntaryById(UUID id) {
        Voluntary entity = voluntaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voluntario não encontrado"));
        return voluntaryMapper.toResponse(entity);
    }

    @Transactional
    public void deleteVoluntary(UUID id) {
        if (!voluntaryRepository.existsById(id)){
            throw new ResourceNotFoundException("Voluntario não encontrado");
        }
        voluntaryRepository.deleteById(id);
    }
}

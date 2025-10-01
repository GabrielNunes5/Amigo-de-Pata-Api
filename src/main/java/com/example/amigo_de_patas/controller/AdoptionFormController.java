package com.example.amigo_de_patas.controller;


import com.example.amigo_de_patas.dto.request.AdoptionFormCreateRequest;
import com.example.amigo_de_patas.dto.response.AdoptionFormResponse;
import com.example.amigo_de_patas.dto.response.ApiResponse;
import com.example.amigo_de_patas.service.AdoptionFormService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/adoption-form")
public class AdoptionFormController {

    @Autowired
    public AdoptionFormService adoptionFormService;

    @PostMapping
    public ResponseEntity<AdoptionFormResponse> createAdoptionForm(@Valid @RequestBody AdoptionFormCreateRequest request){
        AdoptionFormResponse response = adoptionFormService.createAdoptionForm(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{adoptionFormId}")
    public ResponseEntity<AdoptionFormResponse> getAdoptionFormById(@PathVariable UUID adoptionFormId){
        AdoptionFormResponse response = adoptionFormService.getAdoptionFormById(adoptionFormId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/adopter/{adopterId}")
    public ResponseEntity<ApiResponse<Page<AdoptionFormResponse>>> getAdoptionFormsByAdopter(@PathVariable UUID adopterId, Pageable pageable){
        Page<AdoptionFormResponse> responseList = adoptionFormService.getAdoptionFormByAdopterId(adopterId, pageable);
        return ResponseEntity.ok(new ApiResponse<>(responseList));

    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<ApiResponse<Page<AdoptionFormResponse>>> getAdoptionFormsByAnimal(@PathVariable UUID animalId, Pageable pageable){
        Page<AdoptionFormResponse> responseList = adoptionFormService.getAdoptionFormByAnimalId(animalId, pageable);
        return ResponseEntity.ok(new ApiResponse<>(responseList));

    }

}

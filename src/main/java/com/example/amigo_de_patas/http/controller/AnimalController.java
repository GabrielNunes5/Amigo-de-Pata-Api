package com.example.amigo_de_patas.http.controller;

import com.example.amigo_de_patas.dto.request.AnimalCreateRequest;
import com.example.amigo_de_patas.dto.request.AnimalUpdateRequest;
import com.example.amigo_de_patas.dto.response.AnimalResponse;
import com.example.amigo_de_patas.service.AnimalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    public ResponseEntity<AnimalResponse> createAnimal(@Valid @RequestBody AnimalCreateRequest request) {
        System.out.println("Received request: " + request);
        AnimalResponse response = animalService.createAnimal(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AnimalResponse>> getAllAnimals(){
        List<AnimalResponse> responseList = animalService.getAllAnimals();
        return ResponseEntity.ok(responseList);
    }


    @GetMapping("/{animalId}")
    public ResponseEntity<AnimalResponse> getAnimalById(@PathVariable UUID animalId) {
        AnimalResponse response = animalService.getAnimalById(animalId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{animalId}")
    public ResponseEntity<AnimalResponse> updateAnimal(
            @PathVariable UUID animalId,
            @RequestBody @Valid AnimalUpdateRequest request) {
        AnimalResponse updated = animalService.updateAnimal(animalId, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable UUID animalId) {
        animalService.deleteAnimal(animalId);
        return ResponseEntity.noContent().build();
    }

}
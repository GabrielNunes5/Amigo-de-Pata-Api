package com.example.amigo_de_patas.controller;

import com.example.amigo_de_patas.dto.request.VoluntaryCreateRequest;
import com.example.amigo_de_patas.dto.response.ApiResponse;
import com.example.amigo_de_patas.dto.response.VoluntaryResponse;
import com.example.amigo_de_patas.service.VoluntaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/voluntary")
public class VoluntaryController {
    @Autowired
    private VoluntaryService voluntaryService;

    @PostMapping
    public ResponseEntity<VoluntaryResponse> createVoluntary(@Valid @RequestBody VoluntaryCreateRequest request){
        VoluntaryResponse response = voluntaryService.createVoluntary(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<VoluntaryResponse>>> getAllVoluntaries(Pageable pageable){
        Page<VoluntaryResponse> responseList = voluntaryService.getAllVoluntaries(pageable);
        return ResponseEntity.ok(new ApiResponse<>(responseList));
    }

    @GetMapping("/{voluntaryId}")
    public ResponseEntity<ApiResponse<VoluntaryResponse>> getVoluntaryById(@PathVariable UUID voluntaryId){
        VoluntaryResponse response = voluntaryService.getVoluntaryById(voluntaryId);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    @DeleteMapping("/{voluntaryId}")
    public ResponseEntity<Void> deleteVoluntary(@PathVariable UUID voluntaryId){
        voluntaryService.deleteVoluntary(voluntaryId);
        return ResponseEntity.noContent().build();
    }
}

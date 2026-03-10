package com.example.amigo_de_patas.controller;

import com.example.amigo_de_patas.dto.request.AdopterCreateRequest;
import com.example.amigo_de_patas.dto.request.AdopterUpdateRequest;
import com.example.amigo_de_patas.dto.response.AdopterResponse;
import com.example.amigo_de_patas.dto.response.ApiResponse;
import com.example.amigo_de_patas.service.AdopterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/adopters")
public class AdopterController {

    private final AdopterService adopterService;

    public AdopterController(AdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AdopterResponse>> createAdopter(@Valid @RequestBody AdopterCreateRequest request){
        AdopterResponse response = adopterService.createAdopter(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdopterResponse>>> getAllAdopters(Pageable pageable){
        Page<AdopterResponse> responseList = adopterService.getAllAdopters(pageable);
        return ResponseEntity.ok(new ApiResponse<>(responseList));
    }

    @GetMapping("/{adopterId}")
    public ResponseEntity<ApiResponse<AdopterResponse>> getAdopterById(@PathVariable UUID adopterId){
        AdopterResponse response = adopterService.getAdopterById(adopterId);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    @PutMapping("/{adopterId}")
    public ResponseEntity<ApiResponse<AdopterResponse>> updateAdopter(
            @PathVariable UUID adopterId,
            @RequestBody @Valid AdopterUpdateRequest request ){
        AdopterResponse updated = adopterService.updateAdopter(adopterId, request);
        return ResponseEntity.ok(new ApiResponse<>(updated));
    }

    @DeleteMapping("/{adopterId}")
    public ResponseEntity<Void> deleteAdopter(@PathVariable UUID adopterId){
        adopterService.deleteAdopter(adopterId);
        return ResponseEntity.noContent().build();
    }
}

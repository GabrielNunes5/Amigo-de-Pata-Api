package com.example.amigo_de_patas.controller;

import com.example.amigo_de_patas.dto.request.AdopterCreateRequest;
import com.example.amigo_de_patas.dto.request.AuthCreateRequest;
import com.example.amigo_de_patas.dto.response.AdopterResponse;
import com.example.amigo_de_patas.dto.response.ApiResponse;
import com.example.amigo_de_patas.dto.response.AuthResponse;
import com.example.amigo_de_patas.mapper.AdopterMapper;
import com.example.amigo_de_patas.model.Adopter;
import com.example.amigo_de_patas.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AdopterMapper adopterMapper;

    public AuthController(AuthService authService, AdopterMapper adopterMapper) {
        this.authService = authService;
        this.adopterMapper = adopterMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthCreateRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AdopterCreateRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AdopterResponse>> me(Authentication authentication) {
        Adopter adopter = (Adopter) authentication.getPrincipal();
        AdopterResponse response = adopterMapper.toResponse(adopter);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

}



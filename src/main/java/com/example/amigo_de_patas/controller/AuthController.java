package com.example.amigo_de_patas.controller;

import com.example.amigo_de_patas.dto.request.AdopterCreateRequest;
import com.example.amigo_de_patas.dto.request.AuthCreateRequest;
import com.example.amigo_de_patas.dto.response.AdopterResponse;
import com.example.amigo_de_patas.dto.response.ApiResponse;
import com.example.amigo_de_patas.dto.response.AuthResponse;
import com.example.amigo_de_patas.service.AdopterService;
import com.example.amigo_de_patas.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AdopterService adopterService;

    public AuthController(AuthService authService, AdopterService adopterService) {
        this.authService = authService;
        this.adopterService = adopterService;
    }

    private void addAuthCookies(HttpServletResponse response, AuthResponse auth) {

        ResponseCookie accessCookie = ResponseCookie.from("access_token", auth.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(auth.getExpiresIn())
                .sameSite("Strict")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", auth.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(604800)
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid AuthCreateRequest request,
                                      HttpServletResponse response) {

        AuthResponse authResponse = authService.login(request);

        addAuthCookies(response, authResponse);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid AdopterCreateRequest request,
                                         HttpServletResponse response) {

        AuthResponse authResponse = authService.register(request);

        addAuthCookies(response, authResponse);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AdopterResponse>> me(Authentication authentication) {

        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID adopterId = UUID.fromString(jwt.getClaim("adopterId"));

        AdopterResponse adopter = adopterService.getAdopterById(adopterId);

        return ResponseEntity.ok(new ApiResponse<>(adopter));
    }

}



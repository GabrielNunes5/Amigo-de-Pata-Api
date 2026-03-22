package com.example.amigo_de_patas.controller;

import com.example.amigo_de_patas.dto.request.AdopterCreateRequest;
import com.example.amigo_de_patas.dto.request.AuthCreateRequest;
import com.example.amigo_de_patas.dto.request.ForgotPasswordRequest;
import com.example.amigo_de_patas.dto.request.ResetPasswordRequest;
import com.example.amigo_de_patas.dto.response.AdopterResponse;
import com.example.amigo_de_patas.dto.response.ApiResponse;
import com.example.amigo_de_patas.dto.response.AuthResponse;
import com.example.amigo_de_patas.service.AdopterService;
import com.example.amigo_de_patas.service.AuthService;
import com.example.amigo_de_patas.service.PasswordResetTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    private final PasswordResetTokenService passwordResetTokenService;

    public AuthController(AuthService authService, AdopterService adopterService, PasswordResetTokenService passwordResetTokenService) {
        this.authService = authService;
        this.adopterService = adopterService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    private void addAuthCookies(HttpServletResponse response, AuthResponse auth) {

        ResponseCookie accessCookie = ResponseCookie.from("access_token", auth.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(auth.getExpiresIn())
                .sameSite("None")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", auth.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(604800)
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());
    }

    private String getCookieValue(HttpServletRequest request) {

        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("refresh_token")) {
                return cookie.getValue();
            }
        }

        return null;
    }

    private String getClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isEmpty()) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody @Valid AuthCreateRequest request,
            HttpServletResponse response,
            HttpServletRequest httpRequest) {

        String ip = getClientIp(httpRequest);

        AuthResponse authResponse = authService.login(request, ip);

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

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest request,
                                        HttpServletResponse response) {

        String refreshToken = getCookieValue(request);

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        AuthResponse authResponse = authService.refreshToken(refreshToken);

        addAuthCookies(response, authResponse);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        ResponseCookie accessCookie = ResponseCookie.from("access_token", "deleted")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", "deleted")
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(0)
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotpassword(@RequestBody @Valid ForgotPasswordRequest request){
        passwordResetTokenService.forgotPassword(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(
            @RequestBody @Valid ResetPasswordRequest request,
            @RequestParam String resetToken){
        passwordResetTokenService.resetPassword(request, resetToken);
        return ResponseEntity.ok().build();
    }
}



package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.model.Adopter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Value("${jwt.expiration-in-seconds}")
    @Getter
    private Long expirationInSeconds;

    @Value("${jwt.refresh-expiration-in-seconds}")
    @Getter
    private Long refreshExpirationInSeconds;

    public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateAccessToken(UserDetails userDetails) {
        Adopter adopter = (Adopter) userDetails;
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("https://amigo-de-pata-api.onrender.com")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationInSeconds))
                .subject(userDetails.getUsername())
                .claim("adopterId", adopter.getAdopterId().toString())
                .claim("scope", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Adopter adopter = (Adopter) userDetails;
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("https://amigo-de-pata-api.onrender.com")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(refreshExpirationInSeconds))
                .subject(userDetails.getUsername())
                .claim("adopterId", adopter.getAdopterId().toString())
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Jwt decodeToken(String token) {
        return jwtDecoder.decode(token);
    }

}


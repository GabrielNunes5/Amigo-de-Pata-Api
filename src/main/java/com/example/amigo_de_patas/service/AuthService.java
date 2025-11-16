package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.dto.request.AdopterCreateRequest;
import com.example.amigo_de_patas.dto.request.AuthCreateRequest;
import com.example.amigo_de_patas.dto.response.AuthResponse;
import com.example.amigo_de_patas.exceptions.ConflictException;
import com.example.amigo_de_patas.exceptions.UnauthorizedException;
import com.example.amigo_de_patas.model.Adopter;
import com.example.amigo_de_patas.model.Role;
import com.example.amigo_de_patas.repository.AdopterRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final AdopterRepository adopterRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AdopterRepository adopterRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.adopterRepository = adopterRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adopterRepository.findAdopterByAdopterEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public AuthResponse login(AuthCreateRequest request) {
        var adopter = adopterRepository.findAdopterByAdopterEmail(request.getAdopterEmail())
                .orElseThrow(() -> new UnauthorizedException("Credenciais inválidas"));

        if (!passwordEncoder.matches(request.getAdopterPassword(), adopter.getPassword())) {
            throw new UnauthorizedException("Credenciais inválidas");
        }

        String accessToken = jwtService.generateAccessToken(adopter);
        String refreshToken = jwtService.generateRefreshToken(adopter);

        return new AuthResponse(
                accessToken,
                refreshToken,
                jwtService.getExpirationInSeconds(),
                "Bearer"
        );
    }

    public AuthResponse register(AdopterCreateRequest request) {
        if (adopterRepository.findAdopterByAdopterEmail(request.getAdopterEmail()).isPresent()) {
            throw new ConflictException("Email já cadastrado");
        }

        if (adopterRepository.findAdopterByAdopterCPF(request.getAdopterCPF()).isPresent()) {
            throw new ConflictException("CPF já cadastrado");
        }

        if (adopterRepository.findAdopterByAdopterPhone(request.getAdopterPhone()).isPresent()){
            throw new ConflictException("Numero de Telefone ja cadastrado");
        }

        Adopter adopter = new Adopter();
        adopter.setAdopterFullName(request.getAdopterFullName());
        adopter.setAdopterBirthDate(request.getAdopterBirthDate());
        adopter.setAdopterCPF(request.getAdopterCPF());
        adopter.setAdopterEmail(request.getAdopterEmail());
        adopter.setAdopterPassword(passwordEncoder.encode(request.getAdopterPassword()));
        adopter.setAdopterPhone(request.getAdopterPhone());
        adopter.setAdopterAddress(request.getAdopterAddress());
        adopter.setTypeHouse(request.getTypeHouse());
        adopter.setHasGarden(request.getHasGarden());
        adopter.setRole(Role.USER);

        adopterRepository.save(adopter);

        String accessToken = jwtService.generateAccessToken(adopter);
        String refreshToken = jwtService.generateRefreshToken(adopter);

        return new AuthResponse(
                accessToken,
                refreshToken,
                jwtService.getExpirationInSeconds(),
                "Bearer"
        );
    }
}



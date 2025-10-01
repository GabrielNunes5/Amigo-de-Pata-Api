package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.dto.request.AdopterCreateRequest;
import com.example.amigo_de_patas.dto.request.AuthCreateRequest;
import com.example.amigo_de_patas.dto.response.AuthResponse;
import com.example.amigo_de_patas.model.Adopter;
import com.example.amigo_de_patas.model.Role;
import com.example.amigo_de_patas.repository.AdopterRepository;
import org.springframework.security.authentication.BadCredentialsException;
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
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));

        if (!passwordEncoder.matches(request.getAdopterPassword(), adopter.getPassword())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        String token = jwtService.generateToken(adopter);

        return new AuthResponse(
                token,
                jwtService.getExpirationInSeconds(),
                adopter.getAdopterFullName(),
                adopter.getAdopterEmail(),
                adopter.getRole()
        );
    }

    public AuthResponse register(AdopterCreateRequest request) {
        if (adopterRepository.findAdopterByAdopterEmail(request.getAdopterEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
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

        String token = jwtService.generateToken(adopter);

        return new AuthResponse(
                token,
                jwtService.getExpirationInSeconds(),
                adopter.getAdopterFullName(),
                adopter.getAdopterEmail(),
                adopter.getRole()
        );
    }
}



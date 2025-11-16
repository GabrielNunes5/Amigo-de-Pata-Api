package com.example.amigo_de_patas.repository;

import com.example.amigo_de_patas.model.Adopter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter, UUID> {
    Optional<Adopter> findAdopterByAdopterEmail(String adopterEmail);

    Optional<Adopter> findAdopterByAdopterCPF(String adopterCPF);

    Optional<Adopter> findAdopterByAdopterPhone(String adopterPhone);
}

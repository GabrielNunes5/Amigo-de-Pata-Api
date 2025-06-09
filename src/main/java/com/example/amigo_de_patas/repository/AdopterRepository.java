package com.example.amigo_de_patas.repository;

import com.example.amigo_de_patas.model.Adopter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter, UUID> {
}

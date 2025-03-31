package com.example.amigo_de_patas.repository;

import com.example.amigo_de_patas.models.Animals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnimalsRepository extends JpaRepository<Animals, UUID> {
}

package com.example.amigo_de_patas.repository;

import com.example.amigo_de_patas.model.AdoptionForm;
import com.example.amigo_de_patas.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdoptionFormRepository extends JpaRepository<AdoptionForm, UUID> {

    Page<AdoptionForm> findAllByAnimal_AnimalId(UUID animalId, Pageable pageable);

    Page<AdoptionForm> findAllByAdopter_AdopterId(UUID adopterId, Pageable pageable);

    List<AdoptionForm> findAllByAnimal_AnimalIdAndStatus(UUID animalId, Status status);
}

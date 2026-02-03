package com.example.amigo_de_patas.repository;

import com.example.amigo_de_patas.model.Voluntary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoluntaryRepository extends JpaRepository <Voluntary, UUID > {
    Optional<Voluntary> findVoluntaryByVoluntaryEmail(String voluntaryEmail);

    Optional<Voluntary> findVoluntaryByVoluntaryPhone(String voluntaryPhone);
}

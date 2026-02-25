package com.example.amigo_de_patas.repository;

import com.example.amigo_de_patas.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DonationRepository extends JpaRepository<Donation, UUID> {
    Optional<Donation> findByStripeSessionId(String stripeSessionId);
}

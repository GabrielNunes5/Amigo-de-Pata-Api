package com.example.amigo_de_patas.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID donationId;

    @Column(nullable = false)
    private Long donationAmount;

    @Column(nullable = false)
    private String donationCurrency;

    @Column(unique = true)
    private String stripeSessionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonationStatus donationStatus = DonationStatus.PENDING;

    @CreationTimestamp
    private Timestamp createdAt;
}

package com.example.amigo_de_patas.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.security.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name="animals")
public class Animals {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String animalName;

    @Column(nullable = false, length = 50)
    private String animalAge;

    @Column(nullable = false)
    private Double animalWeight;

    @Column(nullable = false, length = 100)
    private String animalNumAge;

    @Column(nullable = false, length = 10)
    private String animalSex;

    @Column(length = 50)
    private String animalColor;

    @Column(length = 50)
    private String animalSpecies;

    @Column
    private String animalVaccines;

    @Column(length = 50)
    private String animalSized;

    @Column
    private Boolean animalNeutered;

    @Column
    private String animalSpecialConditions;

    @Column(nullable = false, length = 100)
    private String animalCategory;

    @Column(nullable = false)
    private String animalImageUrl;

    @Column
    private Boolean animalAdopted = false;

    @ManyToOne
    @JoinColumn
    private Adopter adopter;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}


package com.example.amigo_de_patas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "animal_image")
public class AnimalImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID animalImageId;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animalId")
    private Animal animal;

}

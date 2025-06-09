package com.example.amigo_de_patas.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
import java.util.List;

@Data
@Entity
@Table(name="adopter")
public class Adopter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID adopterId;

    @Column(nullable = false, length = 100)
    private String adopterFullName;

    @Column(nullable = false)
    private Integer adopterAge;

    @Column(nullable = false, unique = true, length = 100)
    private String adopterEmail;

    @Column(nullable = false, unique = true, length = 20)
    private String adopterPhone;

    @Column(nullable = false, length = 200)
    private String adopterAddress;

    @Column(nullable = false, length = 50)
    private String adopterResidenceType;

    @Column
    private Boolean adopterHasGarden = false;

    @Column(length = 100)
    private String adopterOtherPets;

    @Column(nullable = false, length = 100)
    private String adopterPetType;

    @Column(length = 100)
    private String adopterPetPreference;

    @Column(nullable = false, length = 100)
    private String adopterOccupation;

    @Column(nullable = false, length = 50)
    private String adopterWorkHours;

    @Column(nullable = false)
    private Double adopterIncome;

    @Column(nullable = false)
    private String adopterAdoptionReason;

    @Column(nullable = false)
    private String adopterCommitmentToCare;

    @Column
    private String adopterExperienceWithPets;

    @Column
    private String adopterAdditionalInfo;

    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Animal> adoptedAnimals;
}

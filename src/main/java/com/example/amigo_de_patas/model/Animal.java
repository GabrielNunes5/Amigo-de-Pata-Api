package com.example.amigo_de_patas.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID animalId;

    @Column(nullable = false, length = 100)
    private String animalName;

    @Column(nullable = false, length = 50)
    private String animalAge;

    @Column(nullable = false)
    private BigDecimal animalWeight;

    @Column(nullable = false, length = 10)
    private String animalSex;

    @Column(length = 50)
    private String animalColor;

    @Column(length = 50)
    private String animalSpecies;

    @Column
    private String animalVaccines;

    @Column(length = 50)
    private String animalSize;

    @Column
    private Boolean animalNeutered = false;

    @Column
    private String animalSpecialConditions;

    @Column(nullable = false)
    private String animalImageUrl;

    @Column
    private Boolean animalAdopted = false;

    @OneToMany(
            mappedBy = "animal",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AnimalImage> animalImages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}


package com.example.amigo_de_patas.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name="adoption_form")
public class AdoptionForm {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID adoptionFormId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;

    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Column(nullable = false, length = 20)
    private String typeHouse;

    @Column(nullable = false)
    private Boolean hasGarden = false;

    @Column()
    private String experience;

    @Column()
    private String otherAnimals;

    @Column()
    private String message;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}

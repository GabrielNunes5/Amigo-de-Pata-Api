package com.example.amigo_de_patas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.time.LocalDate;
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

    @Past
    @Column(nullable = false)
    private LocalDate adopterBirthDate;

    @Column(nullable = false, unique = true, length = 11)
    private String adopterCPF;

    @Column(nullable = false, unique = true, length = 100)
    private String adopterEmail;

    @Column(nullable = false, unique = true, length = 20)
    private String adopterPhone;

    @Column(nullable = false, length = 200)
    private String adopterAddress;

    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Animal> adoptedAnimals;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}

package com.example.amigo_de_patas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "voluntary")
public class Voluntary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID voluntaryId;

    @Column(nullable = false, length = 100)
    private String voluntaryName;

    @Column(nullable = false, unique = true, length = 100)
    private String voluntaryEmail;

    @Column(nullable = false, unique = true, length = 20)
    private String voluntaryPhone;

    @Past
    @Column(nullable = false)
    private LocalDate voluntaryBirthDate;

    @Column(length = 30)
    private String voluntaryOccupation;

    @Column(nullable = false)
    private String voluntaryInterestArea;

    @Column(nullable = false)
    private String voluntaryAvailability;

    @Column()
    private String voluntaryExperience;

    @Column()
    private String voluntaryMotivation;

    @Column(nullable = false)
    private Boolean hasTransportation = false;

    @Column(nullable = false)
    private Boolean hasRescueExperience = false;

    @Column(nullable = false)
    private Boolean canWorkOnWeekends = false;

    @Column()
    private String voluntarySpecialSkills;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Transient
    public List<String> getVoluntaryInterestArea(){
        if(voluntaryInterestArea == null || voluntaryInterestArea.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(voluntaryInterestArea.split(","));
    }

    public void setVoluntaryInterestArea(List<String> areas){
        this.voluntaryInterestArea = areas != null && !areas.isEmpty()
                ? String.join(",", areas)
                : null;
    }

    @Transient
    public List<String> getVoluntaryAvailability(){
        if(voluntaryAvailability == null || voluntaryAvailability.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(voluntaryAvailability.split(","));
    }

    public void setVoluntaryAvailability(List<String> disp){
        this.voluntaryAvailability = disp != null && !disp.isEmpty()
                ? String.join(",", disp)
                : null;
    }
}

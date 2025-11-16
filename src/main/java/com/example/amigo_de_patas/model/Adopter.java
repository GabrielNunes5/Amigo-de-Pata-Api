package com.example.amigo_de_patas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;
import java.util.List;

@Data
@Entity
@Table(name="adopter")
public class Adopter implements UserDetails {

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

    @Column(nullable = false)
    private String adopterPassword;

    @Column(nullable = false, unique = true, length = 20)
    private String adopterPhone;

    @Column(nullable = false, length = 200)
    private String adopterAddress;

    @Column(nullable = false, length = 20)
    private String typeHouse;

    @Column(nullable = false)
    private Boolean hasGarden = false;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Animal> adoptedAnimals;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == Role.ADMIN) return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER"));
        else return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return adopterPassword;
    }

    @Override
    public String getUsername() {
        return adopterEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

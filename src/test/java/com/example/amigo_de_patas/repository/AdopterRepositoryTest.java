package com.example.amigo_de_patas.repository;

import com.example.amigo_de_patas.model.Adopter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AdopterRepositoryTest {

    @Autowired
    private AdopterRepository adopterRepository;

    @Test
    void shouldSaveAndFindAllAdopter(){
        Adopter adopter = new Adopter();
        adopter.setAdopterFullName("Jhon Doe");
        adopter.setAdopterEmail("jhon@doe.com");
        adopter.setAdopterCPF("61548851302");
        adopter.setAdopterPhone("1234567890");
        adopter.setAdopterBirthDate(LocalDate.of(1990, 1, 1));
        adopter.setAdopterAddress("123 Main Street");
        adopterRepository.save(adopter);

        List<Adopter> adopters = adopterRepository.findAll();

        assertFalse(adopters.isEmpty());
        assertEquals("Jhon Doe", adopters.getFirst().getAdopterFullName());
    }

    @Test
    void shouldSaveAndFindById(){
        Adopter adopter = new Adopter();
        adopter.setAdopterFullName("Jhon Doe");
        adopter.setAdopterEmail("jhon@doe.com");
        adopter.setAdopterCPF("61548851302");
        adopter.setAdopterPhone("1234567890");
        adopter.setAdopterBirthDate(LocalDate.of(1990, 1, 1));
        adopter.setAdopterAddress("123 Main Street");

        Adopter saved = adopterRepository.save(adopter);
        UUID id = saved.getAdopterId();

        Optional<Adopter> found = adopterRepository.findById(id);

        assertTrue(found.isPresent());
        assertEquals("Jhon Doe", found.get().getAdopterFullName());
    }
}

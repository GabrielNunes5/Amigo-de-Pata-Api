package com.example.amigo_de_patas.repository;

import com.example.amigo_de_patas.model.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    @DisplayName("Deve salvar e buscar todos os animais")
    void shouldSaveAndFindAllAnimals() {
        Animal animal = new Animal();
        animal.setAnimalName("Rex");
        animal.setAnimalAge("Jovem");
        animal.setAnimalWeight(BigDecimal.valueOf(18.5));
        animal.setAnimalSex("Macho");
        animal.setAnimalImageUrl("https://exemplo.com/imagem.jpg");
        animalRepository.save(animal);

        List<Animal> animals = animalRepository.findAll();

        assertFalse(animals.isEmpty());
        assertEquals("Rex", animals.getFirst().getAnimalName());
    }

    @Test
    @DisplayName("Deve salvar e buscar por ID")
    void shouldSaveAndFindById() {
        Animal animal = new Animal();
        animal.setAnimalName("Mia");
        animal.setAnimalAge("Jovem");
        animal.setAnimalWeight(BigDecimal.valueOf(4.0));
        animal.setAnimalSex("Fêmea");
        animal.setAnimalSpecies("Gato");
        animal.setAnimalImageUrl("https://exemplo.com/imagem.jpg");

        Animal saved = animalRepository.save(animal);
        UUID id = saved.getAnimalId();

        Optional<Animal> found = animalRepository.findById(id);

        assertTrue(found.isPresent());
        assertEquals("Mia", found.get().getAnimalName());
    }
}

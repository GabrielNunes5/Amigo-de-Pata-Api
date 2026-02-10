package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.dto.request.AnimalCreateRequest;
import com.example.amigo_de_patas.exceptions.BadRequestException;
import com.example.amigo_de_patas.exceptions.ResourceNotFoundException;
import com.example.amigo_de_patas.model.AnimalImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.amigo_de_patas.repository.AnimalRepository;
import com.example.amigo_de_patas.model.Animal;
import com.example.amigo_de_patas.dto.request.AnimalUpdateRequest;
import com.example.amigo_de_patas.dto.response.AnimalResponse;
import com.example.amigo_de_patas.mapper.AnimalMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final CloudinaryService cloudinaryService;

    public AnimalService(AnimalRepository animalRepository, AnimalMapper animalMapper, CloudinaryService cloudinaryService) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @Transactional
    public AnimalResponse createAnimal(AnimalCreateRequest req) {
        Animal entity = animalMapper.toEntity(req);
        Animal saved = animalRepository.save(entity);
        return animalMapper.toResponse(saved);
    }

    @Transactional
    public void uploadImages(UUID id, List<MultipartFile> files){
        if(files == null || files.isEmpty()) {
            throw new BadRequestException("Nenhuma imagem enviada");
        }

        if (files.size() > 10){
            throw new BadRequestException("Máximo de 10 imagens por animal");
        }

        Animal entity = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));

        for (MultipartFile file : files) {

            if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
                throw new BadRequestException("Arquivo Invalido");
            }

            String url = cloudinaryService.uploadImage(file);

            AnimalImage image = new AnimalImage();
            image.setUrl(url);
            image.setAnimal(entity);

            entity.getAnimalImages().add(image);
        }
    }

    public Page<AnimalResponse> getAllAnimals(Pageable pageable) {
        return animalRepository.findAll(pageable)
                .map(animalMapper::toResponse);
    }

    public AnimalResponse getAnimalById(UUID id) {
        Animal entity = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));
        return animalMapper.toResponse(entity);
    }

    @Transactional
    public AnimalResponse updateAnimal(UUID id, AnimalUpdateRequest req) {
        Animal entity = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));
        animalMapper.updateAnimalFromDto(req, entity);
        Animal updated = animalRepository.save(entity);
        return animalMapper.toResponse(updated);
    }

    @Transactional
    public void deleteAnimal(UUID id) {
        if (!animalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Animal não encontrado");
        }
        animalRepository.deleteById(id);
    }
}

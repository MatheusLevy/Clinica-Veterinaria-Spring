package com.produtos.apirest.controller;

import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.models.DTO.AnimalTypeDTO;
import com.produtos.apirest.service.AnimalTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/animalType")
public class AnimalTypeController {

    private final AnimalTypeService typeService;

    public AnimalTypeController(AnimalTypeService typeService) {
        this.typeService = typeService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AnimalTypeDTO save(@RequestBody AnimalTypeDTO animalTypeDTO){
        AnimalType animalType = animalTypeDTO.toAnimalType();
        AnimalType typeSaved = typeService.save(animalType);
        return typeSaved.toAnimalTypeDTO();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable(value = "id") Long id){
        typeService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id){
        AnimalType typeRemoved = typeService.removeByIdWithFeedback(id);
        AnimalTypeDTO dtoResponse = typeRemoved.toAnimalTypeDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AnimalTypeDTO animalTypeDTO){
        AnimalType animalType = animalTypeDTO.toAnimalType();
        AnimalType animalTypeUpdated = typeService.update(animalType);
        AnimalTypeDTO dtoResponse = animalTypeUpdated.toAnimalTypeDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        AnimalType typeFind = typeService.findById(id);
        AnimalTypeDTO dtoResponse = typeFind.toAnimalTypeDTO();
        return ResponseEntity.ok(dtoResponse);
    }
}
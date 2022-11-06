package com.produtos.apirest.controller;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.DTO.OwnerDTO;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.OwnerService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/animal")
public class AnimalController {

    private final AnimalService animalService;
    private final OwnerService ownerService;
    private final AnimalTypeService animalTypeService;

    public AnimalController(AnimalService animalService, OwnerService ownerService, AnimalTypeService animalTypeService){
        this.animalService = animalService;
        this.ownerService = ownerService;
        this.animalTypeService = animalTypeService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AnimalDTO animaldto){
        try{
            Owner ownerFind = ownerService.findById(animaldto.getOwnerId());
            AnimalType typeFind = animalTypeService.findById(animaldto.getAnimalTypeId());
            Animal animal = animaldto.toAnimal(ownerFind, typeFind);
            Animal animalSaved = animalService.save(animal);
            AnimalDTO dtoResponse = animalSaved.toAnimalDTO();
            return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable(value = "id") Long id){
        try{
            animalService.removeById(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id){
        try{
            Animal animalRemoved = animalService.removeByIdWithFeedback(id);
            AnimalDTO dtoResponse = animalRemoved.toAnimalDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        try{
            Animal animalFind = animalService.findById(id);
            AnimalDTO dtoResponse = animalFind.toAnimalDTO();
        return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody  AnimalDTO animalDTO){
        try{
            Owner ownerFind = ownerService.findById(animalDTO.getOwnerId());
            AnimalType typeFind = animalTypeService.findById(animalDTO.getAnimalTypeId());
            Animal animal = animalDTO.toAnimal(ownerFind, typeFind);
            Animal animalUpdated = animalService.update(animal);
            AnimalDTO dtoResponse = animalUpdated.toAnimalDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<?> findOwnerByAnimalId(@PathVariable(value = "id") Long id){
        try {
            Owner ownerFind = animalService.findOwnerByAnimalId(id);
            OwnerDTO dtoResponse = ownerFind.toOwnerDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/owner")
    public ResponseEntity<?> updateOwner(@RequestBody AnimalDTO animalDTO){
        try {
            Owner ownerFind = ownerService.findById(animalDTO.getOwnerId());
            Animal animalFind = animalService.findById(animalDTO.getId());
            Animal animalUpdated = animalService.updateOwner(animalFind, ownerFind);
            AnimalDTO dtoResponse = animalUpdated.toAnimalDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
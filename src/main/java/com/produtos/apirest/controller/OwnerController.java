package com.produtos.apirest.controller;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.DTO.OwnerDTO;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.service.OwnerService;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService){
        this.ownerService = ownerService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OwnerDTO save(@RequestBody OwnerDTO ownerDTO){
        Owner owner = ownerDTO.toOwner();
        Owner ownerSaved = ownerService.save(owner);
        return ownerSaved.toOwnerDTO();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody OwnerDTO ownerDTO){
        Owner owner = ownerDTO.toOwner();
        Owner ownerUpdated = ownerService.update(owner);
        OwnerDTO dtoResponse = ownerUpdated.toOwnerDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable(value = "id") Long id){
        ownerService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Owner> owners = ownerService.findAll();
        List<OwnerDTO> dtosResponse = owners
                .stream()
                .map(Owner::toOwnerDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtosResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        Owner ownerFind = ownerService.findById(id);
        OwnerDTO dtoResponse = ownerFind.toOwnerDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id){
        Owner ownerRemoved = ownerService.removeByIdWithFeedback(id);
        OwnerDTO dtoResponse = ownerRemoved.toOwnerDTO();
        return  ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> find(@RequestBody OwnerDTO dto){
        Owner filter = dto.toOwner();
        List<Owner> ownersFind = ownerService.find(filter);
        List<OwnerDTO> dtosResponse = ownersFind
                .stream()
                .map(Owner::toOwnerDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtosResponse);
    }

    @GetMapping("/animals/{id}")
    public ResponseEntity<?> findAllAnimals(@PathVariable(value = "id") Long id){
        List<Animal> animals  =  ownerService.findAllAnimalsByOwnerId(id);
        List<AnimalDTO> dtosResponse = animals
                .stream()
                .map(Animal::toAnimalDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtosResponse);
    }
}
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

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OwnerDTO donodto){
        try{
            Owner owner = donodto.toOwner();
            Owner ownerSaved = ownerService.save(owner);
            OwnerDTO dtoResponse = ownerSaved.toOwnerDTO();
            return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody OwnerDTO donodto){
        try{
            Owner owner = donodto.toOwner();
            Owner ownerUpdated = ownerService.update(owner);
            OwnerDTO dtoResponse = ownerUpdated.toOwnerDTO();
            return ResponseEntity.ok(dtoResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable(value = "id") Long id){
        try{
            ownerService.removeById(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
        try{
            Owner ownerFind = ownerService.findById(id);
            OwnerDTO dtoResponse = ownerFind.toOwnerDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id){
        try{
            Owner ownerRemoved = ownerService.removeByIdWithFeedback(id);
            OwnerDTO dtoResponse = ownerRemoved.toOwnerDTO();
            return  ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> find(@RequestBody OwnerDTO dto){
        try{
            Owner filter = dto.toOwner();
            List<Owner> ownersFind = ownerService.find(filter);
            List<OwnerDTO> dtosResponse = ownersFind
                    .stream()
                    .map(Owner::toOwnerDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtosResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/animals/{id}")
    public ResponseEntity<?> findAllAnimals(@PathVariable(value = "id") Long id){
        try{
            List<Animal> animals  =  ownerService.findAllAnimalsByOwnerId(id);
            List<AnimalDTO> dtosResponse = animals
                    .stream()
                    .map(Animal::toAnimalDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtosResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
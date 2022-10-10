package com.produtos.apirest.controller;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.DonoService;
import com.produtos.apirest.service.Tipo_animalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animal")
public class AnimalController {

    @Autowired
    public AnimalService animalService;

    @Autowired
    public DonoService donoService;

    @Autowired
    public Tipo_animalService tipoAnimalService;

    @PreAuthorize("hasRole('S')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody AnimalDTO animaldto){
        try{
            Dono dono = donoService.buscarDonoPorId(Dono.builder().donoId(animaldto.getIdDono()).build());
            TipoAnimal tipo = tipoAnimalService.buscarTipo_animalPorId(TipoAnimal.builder().tipoAnimalId(animaldto.getIdTipoAnimal()).build());
            Animal animal = Animal.builder().nome(animaldto.getNome()).tipoAnimal(tipo).dono(dono).build();
            Animal animalSalvo = animalService.salvar(animal);
            return new ResponseEntity(animalSalvo, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity remover(@PathVariable(value = "id", required = true) Long id){
        try{
            Animal animal = Animal.builder().animalId(id).build();
            animalService.removerPorId(animal);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: ### ** Substituir Animal por AnimalDTO **
    @PreAuthorize("hasRole('S')")
    @DeleteMapping("/remover/feedback")
    public ResponseEntity removerComFeedback(@RequestBody Animal animal){
        try{
            Animal animalRemovido = animalService.removerFeedback(animal);
            return ResponseEntity.ok(animalRemovido);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscarId/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        Animal animal = Animal.builder().animalId(id).build();
        Animal animalBuscado = animalService.buscarPorId(animal);
        return ResponseEntity.ok(animalBuscado);
    }

    //TODO: ### ** Substituir Animal por AnimalDTO **
    @PreAuthorize("hasRole('S')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody  Animal animal){
        try{
            Animal atualizado = animalService.atualizar(animal);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscarDono/{id}")
    public ResponseEntity buscarDono(@PathVariable(value = "id", required = true) Long id){
        try {
            Animal animal = Animal.builder().animalId(id).build();
            Dono dono = animalService.buscarDono(animal);
            return ResponseEntity.ok(dono);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @PutMapping("/atualizar/dono")
    public ResponseEntity atualizarDono(@RequestBody AnimalDTO animalDTO){
        try {
            Dono dono = Dono.builder().donoId(animalDTO.getIdDono()).build();
            Dono donoBuscado = donoService.buscarDonoPorId(dono);
            Animal animal = Animal.builder().animalId(animalDTO.getId()).build();
            Animal animalBuscado = animalService.buscarPorId(animal);
            Animal animaAtualizado = animalService.atualizarDono(donoBuscado, animalBuscado);
            return ResponseEntity.ok(animaAtualizado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

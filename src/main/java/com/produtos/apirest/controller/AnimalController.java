package com.produtos.apirest.controller;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.DTO.OwnerDTO;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.DonoService;
import com.produtos.apirest.service.TipoAnimalService;
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
    private final DonoService donoService;
    private final TipoAnimalService tipoAnimalService;

    public AnimalController(AnimalService animalService, DonoService donoService, TipoAnimalService tipoAnimalService){
        this.animalService = animalService;
        this.donoService = donoService;
        this.tipoAnimalService = tipoAnimalService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody AnimalDTO animaldto){
        try{
            Owner donoEncontrado = donoService.buscarPorId(animaldto.getOwnerId());
            AnimalType tipoEncontrado = tipoAnimalService.buscarPorId(animaldto.getAnimalTypeId());
            Animal animal = animaldto.toAnimal(donoEncontrado, tipoEncontrado);
            Animal animalSalvo = animalService.salvar(animal);
            AnimalDTO dtoRetorno = animalSalvo.toAnimalDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id") Long id){
        try{
            animalService.removerPorId(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try{
            Animal animalRemovido = animalService.removerComFeedback(id);
            AnimalDTO dtoRetorno = animalRemovido.toAnimalDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarId/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") Long id){
        try{
            Animal animalBuscado = animalService.buscarPorId(id);
            AnimalDTO dtoRetorno = animalBuscado.toAnimalDTO();
        return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody  AnimalDTO animalDTO){
        try{
            Owner donoEncontrado = donoService.buscarPorId(animalDTO.getOwnerId());
            AnimalType tipoEncontrado = tipoAnimalService.buscarPorId(animalDTO.getAnimalTypeId());
            Animal animal = animalDTO.toAnimal(donoEncontrado, tipoEncontrado);
            Animal atualizado = animalService.atualizar(animal);
            AnimalDTO dtoRetorno = atualizado.toAnimalDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarDono/{id}")
    public ResponseEntity<?> buscarDono(@PathVariable(value = "id") Long id){
        try {
            Owner donoEncontrado = animalService.buscarDonoPorId(id);
            OwnerDTO dtoRetorno = donoEncontrado.toOwnerDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/dono")
    public ResponseEntity<?> atualizarDono(@RequestBody AnimalDTO animalDTO){
        try {
            Owner donoBuscado = donoService.buscarPorId(animalDTO.getOwnerId());
            Animal animalBuscado = animalService.buscarPorId(animalDTO.getId());
            Animal animaAtualizado = animalService.atualizarDono(animalBuscado, donoBuscado);
            AnimalDTO dtoRetorno = animaAtualizado.toAnimalDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
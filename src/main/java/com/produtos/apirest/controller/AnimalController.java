package com.produtos.apirest.controller;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.DTO.DonoDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.DonoService;
import com.produtos.apirest.service.TipoAnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            Dono donoEncontrado = donoService.buscarPorId(animaldto.getIdDono());
            TipoAnimal tipoEncontrado = tipoAnimalService.buscarPorId(animaldto.getIdTipoAnimal());
            Animal animal = animaldto.toAnimal(donoEncontrado, tipoEncontrado);
            Animal animalSalvo = animalService.salvar(animal);
            AnimalDTO dtoRetorno = animalSalvo.toDTO();
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
            AnimalDTO dtoRetorno = animalRemovido.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarId/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") Long id){
        try{
            Animal animalBuscado = animalService.buscarPorId(id);
            AnimalDTO dtoRetorno = animalBuscado.toDTO();
        return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody  AnimalDTO animalDTO){
        try{
            Dono donoEncontrado = donoService.buscarPorId(animalDTO.getIdDono());
            TipoAnimal tipoEncontrado = tipoAnimalService.buscarPorId(animalDTO.getIdTipoAnimal());
            Animal animal = animalDTO.toAnimal(donoEncontrado, tipoEncontrado);
            Animal atualizado = animalService.atualizar(animal);
            AnimalDTO dtoRetorno = atualizado.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarDono/{id}")
    public ResponseEntity<?> buscarDono(@PathVariable(value = "id") Long id){
        try {
            Dono donoEncontrado = animalService.buscarDonoPorId(id);
            DonoDTO dtoRetorno = donoEncontrado.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/dono")
    public ResponseEntity<?> atualizarDono(@RequestBody AnimalDTO animalDTO){
        try {
            Dono donoBuscado = donoService.buscarPorId(animalDTO.getIdDono());
            Animal animalBuscado = animalService.buscarPorId(animalDTO.getId());
            Animal animaAtualizado = animalService.atualizarDono(animalBuscado, donoBuscado);
            AnimalDTO dtoRetorno = animaAtualizado.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
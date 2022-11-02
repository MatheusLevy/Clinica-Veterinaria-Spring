package com.produtos.apirest.controller;

import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.models.DTO.AnimalTypeDTO;
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
@RequestMapping("api/tipoAnimal")
public class TipoAnimalController {

    private final TipoAnimalService tipoService;

    public TipoAnimalController(TipoAnimalService tipoService) {
        this.tipoService = tipoService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody AnimalTypeDTO animalTypeDTO){
        try{
            AnimalType tipoAnimal = animalTypeDTO.toAnimalType();
            AnimalType tipoSalvo = tipoService.save(tipoAnimal);
            AnimalTypeDTO dtoRetorno = tipoSalvo.toAnimalTypeDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id") Long id){
        try{
            tipoService.removeById(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try{
            AnimalType tipoRemovido = tipoService.removeByIdWithFeedback(id);
            AnimalTypeDTO dtoRetorno = tipoRemovido.toAnimalTypeDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody AnimalTypeDTO animalTypeDTO){
        try{
            AnimalType tipoAnimal = animalTypeDTO.toAnimalType();
            AnimalType tipoAnimalAtualizado = tipoService.update(tipoAnimal);
            AnimalTypeDTO dtoRetorno = tipoAnimalAtualizado.toAnimalTypeDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<?> buscar(@PathVariable(value = "id") Long id){
        try{
            AnimalType tipoBuscado = tipoService.findById(id);
            AnimalTypeDTO dtoRetorno = tipoBuscado.toAnimalTypeDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
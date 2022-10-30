package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.TipoAnimalDTO;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.service.TipoAnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tipoAnimal")
public class TipoAnimalController {

    private final TipoAnimalService tipoService;

    public TipoAnimalController(TipoAnimalService tipoService) {
        this.tipoService = tipoService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody TipoAnimalDTO tipoAnimalDTO){
        try{
            TipoAnimal tipoAnimal = tipoAnimalDTO.toTipoAnimal();
            TipoAnimal tipoSalvo = tipoService.salvar(tipoAnimal);
            TipoAnimalDTO dtoRetorno = tipoSalvo.toTipoAnimalDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id") Long id){
        try{
            tipoService.removerPorId(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try{
            TipoAnimal tipoRemovido = tipoService.removerComFeedback(id);
            TipoAnimalDTO dtoRetorno = tipoRemovido.toTipoAnimalDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody TipoAnimalDTO tipoAnimalDTO){
        try{
            TipoAnimal tipoAnimal = tipoAnimalDTO.toTipoAnimal();
            TipoAnimal tipoAnimalAtualizado = tipoService.atualizar(tipoAnimal);
            TipoAnimalDTO dtoRetorno = tipoAnimalAtualizado.toTipoAnimalDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<?> buscar(@PathVariable(value = "id") Long id){
        try{
            TipoAnimal tipoBuscado = tipoService.buscarPorId(id);
            TipoAnimalDTO dtoRetorno = tipoBuscado.toTipoAnimalDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
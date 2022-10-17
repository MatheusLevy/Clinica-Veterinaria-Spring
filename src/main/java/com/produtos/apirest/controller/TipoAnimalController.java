package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.TipoAnimalDTO;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.service.TipoAnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tipo_animal")
public class TipoAnimalController {

    @Autowired
    public TipoAnimalService tipoService;

    @PreAuthorize("hasRole('A')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody TipoAnimalDTO dto){
        try{
            TipoAnimal tipoAnimal = TipoAnimal.builder()
                    .nome(dto.getNome())
                    .build();
            TipoAnimal tipoSalvo = tipoService.salvar(tipoAnimal);

            TipoAnimalDTO dtoRetorno = TipoAnimalDTO.builder()
                    .id(tipoSalvo.getTipoAnimalId())
                    .nome(tipoSalvo.getNome())
                    .build();
            return new ResponseEntity(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity remover(@PathVariable(value = "id", required = true) Long id){
        try{
            tipoService.removerPorId(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity removerComFeedback(@PathVariable(value = "id", required = true) Long id){
        try{
            TipoAnimal tipoRemovido = tipoService.removerFeedback(id);
            TipoAnimalDTO dtoRetorno = TipoAnimalDTO.builder()
                    .id(tipoRemovido.getTipoAnimalId())
                    .nome(tipoRemovido.getNome())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody TipoAnimalDTO dto){
        try{
            TipoAnimal tipoAnimal = TipoAnimal.builder()
                    .tipoAnimalId(dto.getId())
                    .nome(dto.getNome())
                    .build();
            TipoAnimal atualizado = tipoService.atualizar(tipoAnimal);

            TipoAnimalDTO dtoRetorno = TipoAnimalDTO.builder()
                    .id(atualizado.getTipoAnimalId())
                    .nome(atualizado.getNome())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity buscar(@PathVariable(value = "id", required = true) Long id){
        try{
            TipoAnimal tipoBuscado = tipoService.buscarTipoAnimalPorId(id);

            TipoAnimalDTO dtoRetorno = TipoAnimalDTO.builder()
                    .id(tipoBuscado.getTipoAnimalId())
                    .nome(tipoBuscado.getNome())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

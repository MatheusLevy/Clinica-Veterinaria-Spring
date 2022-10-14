package com.produtos.apirest.controller;

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

    //TOOD: ### ** Substituir TipoAnimal por TipoAnimalDTO **
    @PreAuthorize("hasRole('A')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody TipoAnimal tipo_animal){
        try{
            TipoAnimal tipoSalvo = tipoService.salvar(tipo_animal);
            return new ResponseEntity(tipoSalvo, HttpStatus.CREATED);
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

    //TOOD: ### ** Substituir TipoAnimal por TipoAnimalDTO **
    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/feedback")
    public ResponseEntity removerComFeedback(@RequestBody TipoAnimal tipo){
        try{
            TipoAnimal tipoRemovido = tipoService.removerFeedback(tipo.getTipoAnimalId());
            return ResponseEntity.ok(tipoRemovido);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TOOD: ### ** Substituir TipoAnimal por TipoAnimalDTO **
    @PreAuthorize("hasRole('A')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody TipoAnimal tipo_animal){
        try{
            TipoAnimal atualizado = tipoService.atualizar(tipo_animal);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity buscar(@PathVariable(value = "id", required = true) Long id){
        try{
            TipoAnimal tipoBuscado = tipoService.buscarTipoAnimalPorId(id);
            return ResponseEntity.ok(tipoBuscado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

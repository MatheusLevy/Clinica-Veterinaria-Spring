package com.produtos.apirest.controller;

import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.service.Tipo_animalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tipo_animal")
public class TipoAnimalController {

    @Autowired
    public Tipo_animalService tipoService;

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody TipoAnimal tipo_animal){
        try{
            TipoAnimal tipoSalvo = tipoService.salvar(tipo_animal);
            return new ResponseEntity(tipoSalvo, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletar(@PathVariable(value = "id", required = true) Long id){
        try{
            TipoAnimal tipo = TipoAnimal.builder().tipoAnimalId(id).build();
            tipoService.removerPorId(tipo);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody TipoAnimal tipo_animal){
        try{
            TipoAnimal atualizado = tipoService.atualizar(tipo_animal);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity buscar(@PathVariable(value = "id", required = true) Long id){
        try{
            TipoAnimal tipo = TipoAnimal.builder().tipoAnimalId(id).build();
            TipoAnimal tipoBuscado = tipoService.buscarTipo_animalPorId(tipo);
            return ResponseEntity.ok(tipoBuscado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

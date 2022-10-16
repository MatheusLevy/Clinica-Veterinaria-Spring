package com.produtos.apirest.controller;

import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinario")
public class VeterinarioController {

    @Autowired
    public VeterinarioService veterinarioService;

    //TODO: ### ** Substituir o Veterinario por VeterinarioDTO **
    @PreAuthorize("hasRole('S')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody Veterinario veterinario){
        try{
            Veterinario vetSalvo = veterinarioService.salvar(veterinario);
            return ResponseEntity.ok(vetSalvo);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity removerComId(@PathVariable(value = "id", required = true) Long id){
        try {
            Veterinario veterinarioBuscado = veterinarioService.buscarPorId(id);
            veterinarioService.remover(veterinarioBuscado);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: ### ** Substituir o Veterinario por VeterinarioDTO **
    @PreAuthorize("hasRole('S')")
    @DeleteMapping("/remover/feedback")
    public ResponseEntity removerComFeedback(@RequestBody Veterinario veterinario){
        try{
            Veterinario vetRemovido = veterinarioService.removerFeedback(veterinario);
            return ResponseEntity.ok(vetRemovido);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try{
            Veterinario vetBuscado = veterinarioService.buscarPorId(id);
            return ResponseEntity.ok(vetBuscado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: ### ** Substituir o Veterinario por VeterinarioDTO **
    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscarTodos")
    public ResponseEntity buscarTodos(){
        try{
            List<Veterinario> veterinarioList = veterinarioService.buscarTodos();
            return ResponseEntity.ok(veterinarioList);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: ### ** Substituir o Veterinario por VeterinarioDTO **
    @PreAuthorize("hasRole('S')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody Veterinario veterinario){
        try{
            Veterinario atualizado = veterinarioService.atualizar(veterinario);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}

package com.produtos.apirest.controller;

import com.produtos.apirest.models.Dono;
import com.produtos.apirest.service.DonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAKey;

@RestController
@RequestMapping("/api/dono")
public class DonoController {

    @Autowired
    public DonoService donoService;

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody Dono dono){
        try{
            Dono donoSalvo = donoService.salvar(dono);
            return new ResponseEntity(donoSalvo, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody Dono dono){
        try{
            Dono donoAtualizado = donoService.atualizar(dono);
            return new ResponseEntity(donoAtualizado, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/id")
    public ResponseEntity remover(@PathVariable(value = "id", required = true) Long id){
        try{
            Dono dono = Dono.builder().donoId(id).build();
            donoService.removerPorId(dono);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try{
            Dono dono = Dono.builder().donoId(id).build();
            Dono donoBuscado = donoService.buscarDonoPorId(dono);
            return ResponseEntity.ok(donoBuscado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

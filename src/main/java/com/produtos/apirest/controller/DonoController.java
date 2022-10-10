package com.produtos.apirest.controller;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.service.DonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.security.interfaces.RSAKey;
import java.util.List;

@RestController
@RequestMapping("/api/dono")
public class DonoController {

    @Autowired
    public DonoService donoService;

    @PreAuthorize("hasRole('S')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody Dono dono){
        try{
            Dono donoSalvo = donoService.salvar(dono);
            return new ResponseEntity(donoSalvo, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody Dono dono){
        try{
            Dono donoAtualizado = donoService.atualizar(dono);
            return new ResponseEntity(donoAtualizado, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity remover(@PathVariable(value = "id", required = true) Long id){
        try{
            Dono dono = Dono.builder().donoId(id).build();
            donoService.removerPorId(dono);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscarTodos")
    public ResponseEntity buscarTodos(){
        return ResponseEntity.ok(donoService.buscarTodos());
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
    @PreAuthorize("hasRole('S')")
    @DeleteMapping("remover/feedback/")
    public ResponseEntity removerComFeedback(@RequestBody Dono dono){
        try{
            Dono removido = donoService.removerFeedback(dono);
            return  ResponseEntity.ok(removido);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscar/filtro")
    public ResponseEntity buscarUtilizandoFiltro(@RequestBody Dono filtro){
        try{
            List<Dono> buscado = donoService.buscar(filtro);
            return ResponseEntity.ok(buscado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @GetMapping("/animais/{id}")
    public ResponseEntity buscarTodosAnimais(@PathVariable(value = "id", required = true) Long id){
        try{
            Dono dono = Dono.builder().donoId(id).build();
            List<Animal> animais  =  donoService.buscarTodosAnimais(dono);
            return ResponseEntity.ok(animais);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

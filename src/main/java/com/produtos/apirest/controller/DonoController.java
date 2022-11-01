package com.produtos.apirest.controller;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.DTO.OwnerDTO;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.service.DonoService;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dono")
public class DonoController {

    private final DonoService donoService;

    public DonoController(DonoService donoService){
        this.donoService = donoService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody OwnerDTO donodto){
        try{
            Owner dono = donodto.toOwner();
            Owner donoSalvo = donoService.salvar(dono);
            OwnerDTO dtoRetorno = donoSalvo.toOwnerDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody OwnerDTO donodto){
        try{
            Owner dono = donodto.toOwner();
            Owner donoAtualizado = donoService.atualizar(dono);
            OwnerDTO dtoRetorno = donoAtualizado.toOwnerDTO();
            return ResponseEntity.ok(dtoRetorno);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id") Long id){
        try{
            donoService.removerPorId(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<?> buscarTodos(){
        List<Owner> donos = donoService.buscarTodos();
        List<OwnerDTO> donosDTOS = donos
                .stream()
                .map(Owner::toOwnerDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(donosDTOS);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") Long id){
        try{
            Owner donoBuscado = donoService.buscarPorId(id);
            OwnerDTO dtoRetorno = donoBuscado.toOwnerDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try{
            Owner removido = donoService.removerComFeedback(id);
            OwnerDTO dtoRetorno = removido.toOwnerDTO();
            return  ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/filtro")
    public ResponseEntity<?> buscarUtilizandoFiltro(@RequestBody OwnerDTO dto){
        try{
            Owner filtro = dto.toOwner();
            List<Owner> donosEncontrados = donoService.buscar(filtro);
            List<OwnerDTO> dtosRetorno = donosEncontrados
                    .stream()
                    .map(Owner::toOwnerDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtosRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/animais/{id}")
    public ResponseEntity<?> buscarTodosAnimais(@PathVariable(value = "id") Long id){
        try{
            List<Animal> animais  =  donoService.buscarTodosAnimais(id);
            List<AnimalDTO> dtosRetorno = animais
                    .stream()
                    .map(Animal::toAnimalDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtosRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
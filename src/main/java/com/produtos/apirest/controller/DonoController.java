package com.produtos.apirest.controller;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.DTO.DonoDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.service.DonoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> salvar(@RequestBody DonoDTO donodto){
        try{
            Dono dono = donodto.toDono();
            Dono donoSalvo = donoService.salvar(dono);
            DonoDTO dtoRetorno = donoSalvo.toDonoDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody DonoDTO donodto){
        try{
            Dono dono = donodto.toDono();
            Dono donoAtualizado = donoService.atualizar(dono);
            DonoDTO dtoRetorno = donoAtualizado.toDonoDTO();
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
        List<Dono> donos = donoService.buscarTodos();
        List<DonoDTO> donosDTOS = donos
                .stream()
                .map(Dono::toDonoDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(donosDTOS);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") Long id){
        try{
            Dono donoBuscado = donoService.buscarPorId(id);
            DonoDTO dtoRetorno = donoBuscado.toDonoDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try{
            Dono removido = donoService.removerComFeedback(id);
            DonoDTO dtoRetorno = removido.toDonoDTO();
            return  ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/filtro")
    public ResponseEntity<?> buscarUtilizandoFiltro(@RequestBody DonoDTO dto){
        try{
            Dono filtro = dto.toDono();
            List<Dono> donosEncontrados = donoService.buscar(filtro);
            List<DonoDTO> dtosRetorno = donosEncontrados
                    .stream()
                    .map(Dono::toDonoDTO)
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
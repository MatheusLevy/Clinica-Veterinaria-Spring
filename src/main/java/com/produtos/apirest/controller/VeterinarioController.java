package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.VeterinarioDTO;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.service.EspecialidadeService;
import com.produtos.apirest.service.VeterinarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/veterinario")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    private final EspecialidadeService especialidadeService;

    public VeterinarioController(VeterinarioService veterinarioService, EspecialidadeService especialidadeService) {
        this.veterinarioService = veterinarioService;
        this.especialidadeService = especialidadeService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody VeterinarioDTO dto){
        try{
            Especialidade especialidadeEncontrada = especialidadeService.buscarPorId(dto.getEspecialidadeId());
            Veterinario veterinario = dto.toVeterinario(especialidadeEncontrada);
            Veterinario veterinarioSalvo = veterinarioService.salvar(veterinario);
            VeterinarioDTO dtoRetorno = veterinarioSalvo.toVeterinarioDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody VeterinarioDTO dto){
        try{
            Especialidade especialidadeEncontrada = especialidadeService.buscarPorId(dto.getEspecialidadeId());
            Veterinario veterinario = dto.toVeterinario(especialidadeEncontrada);
            Veterinario veterinarioAtualizado = veterinarioService.atualizar(veterinario);
            VeterinarioDTO retornoDTO = veterinarioAtualizado.toVeterinarioDTO();
            return ResponseEntity.ok(retornoDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removerComId(@PathVariable(value = "id") Long id){
        try {
            veterinarioService.remover(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try{
            Veterinario veterinarioFeedback = veterinarioService.removerComFeedback(id);
            VeterinarioDTO dtoRetorno = veterinarioFeedback.toVeterinarioDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") Long id){
        try{
            Veterinario veterinarioEncontrado = veterinarioService.buscarPorId(id);
            VeterinarioDTO dtoRetorno = veterinarioEncontrado.toVeterinarioDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<?> buscarTodos(){
        try{
            List<Veterinario> veterinariosList = veterinarioService.buscarTodos();
            List<VeterinarioDTO> dtosRetorno = veterinariosList
                    .stream()
                    .map(Veterinario::toVeterinarioDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtosRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.VeterinaryDTO;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.service.EspecialidadeService;
import com.produtos.apirest.service.VeterinarioService;
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
@RequestMapping("/api/veterinario")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    private final EspecialidadeService especialidadeService;

    public VeterinarioController(VeterinarioService veterinarioService, EspecialidadeService especialidadeService) {
        this.veterinarioService = veterinarioService;
        this.especialidadeService = especialidadeService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody VeterinaryDTO dto){
        try{
            Expertise especialidadeEncontrada = especialidadeService.findById(dto.getExpertiseId());
            Veterinary veterinario = dto.toVeterinary(especialidadeEncontrada);
            Veterinary veterinarioSalvo = veterinarioService.save(veterinario);
            VeterinaryDTO dtoRetorno = veterinarioSalvo.toVeterinaryDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody VeterinaryDTO dto){
        try{
            Expertise especialidadeEncontrada = especialidadeService.findById(dto.getExpertiseId());
            Veterinary veterinario = dto.toVeterinary(especialidadeEncontrada);
            Veterinary veterinarioAtualizado = veterinarioService.update(veterinario);
            VeterinaryDTO retornoDTO = veterinarioAtualizado.toVeterinaryDTO();
            return ResponseEntity.ok(retornoDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removerComId(@PathVariable(value = "id") Long id){
        try {
            veterinarioService.removeById(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try{
            Veterinary veterinarioFeedback = veterinarioService.removeByIdWithFeedback(id);
            VeterinaryDTO dtoRetorno = veterinarioFeedback.toVeterinaryDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") Long id){
        try{
            Veterinary veterinarioEncontrado = veterinarioService.findById(id);
            VeterinaryDTO dtoRetorno = veterinarioEncontrado.toVeterinaryDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<?> buscarTodos(){
        try{
            List<Veterinary> veterinariosList = veterinarioService.findAll();
            List<VeterinaryDTO> dtosRetorno = veterinariosList
                    .stream()
                    .map(Veterinary::toVeterinaryDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtosRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
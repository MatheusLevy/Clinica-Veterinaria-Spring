package com.produtos.apirest.controller;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.EspecialidadeDTO;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.service.AreaService;
import com.produtos.apirest.service.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/especialidade")
public class EspecialidadeController {

    @Autowired
    public EspecialidadeService especialidadeService;

    @Autowired
    public AreaService areaService;

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody EspecialidadeDTO dto){
        try {
            Area areaBuscada = areaService.buscarPorId(dto.getIdArea());

            Especialidade especialidade = dto.toEspecialidade();
            Especialidade especialidadeSalva = especialidadeService.salvar(especialidade);
            EspecialidadeDTO dtoRetorno = especialidadeSalva.toDTO();
            return new ResponseEntity(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody EspecialidadeDTO dto){
        try{
            Especialidade especialidade = dto.toEspecialidade();
            Especialidade especialidadeAtualizada = especialidadeService.atualizar(especialidade);
            EspecialidadeDTO dtoRetorno = especialidadeAtualizada.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id", required = true) Long id){
        try {
            Especialidade especialidadeBuscada = especialidadeService.buscarPorId(id);
            EspecialidadeDTO dtoRetorno = especialidadeBuscada.toDTO();
            especialidadeService.remover(especialidadeBuscada);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id", required = true) Long id){
        try {
            Especialidade especialidadeRemovida = especialidadeService.removerFeedback(id);
            EspecialidadeDTO dtoRetorno = especialidadeRemovida.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try {
            Especialidade especialidadeBuscada = especialidadeService.buscarPorId(id);
            EspecialidadeDTO dtoRetorno = especialidadeBuscada.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/filtro")
    public ResponseEntity<?> buscar(@RequestBody EspecialidadeDTO dto){
        try{
            Especialidade filtro = dto.toEspecialidade();
            List<Especialidade> especialidades = especialidadeService.buscar(filtro);
            List<EspecialidadeDTO> dtos = especialidades
                    .stream()
                    .map(Especialidade::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscarTodos")
    public ResponseEntity<?> buscarTodos(){
        try{
            List<Especialidade> especialidadeList = especialidadeService.buscarTodos();
            List<EspecialidadeDTO> dtos = especialidadeList
                    .stream()
                    .map(Especialidade::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
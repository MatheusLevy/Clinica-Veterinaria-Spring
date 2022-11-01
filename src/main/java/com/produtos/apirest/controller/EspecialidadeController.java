package com.produtos.apirest.controller;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.ExpertiseDTO;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.service.AreaService;
import com.produtos.apirest.service.EspecialidadeService;
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
@RequestMapping("/api/especialidade")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;
    private final AreaService areaService;

    public EspecialidadeController(EspecialidadeService especialidadeService, AreaService areaService){
        this.especialidadeService = especialidadeService;
        this.areaService = areaService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody ExpertiseDTO dto){
        try {
            Area areaBuscada = areaService.buscarPorId(dto.getAreaId());
            Expertise especialidade = dto.toExpertise(areaBuscada);
            Expertise especialidadeSalva = especialidadeService.salvar(especialidade);
            ExpertiseDTO dtoRetorno = especialidadeSalva.toExpertiseDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody ExpertiseDTO dto){
        try{
            Area areaBuscada = areaService.buscarPorId(dto.getAreaId());
            Expertise especialidade = dto.toExpertise(areaBuscada);
            Expertise especialidadeAtualizada = especialidadeService.atualizar(especialidade);
            ExpertiseDTO dtoRetorno = especialidadeAtualizada.toExpertiseDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id") Long id){
        try {
            Expertise especialidadeBuscada = especialidadeService.buscarPorId(id);
            especialidadeService.remover(especialidadeBuscada);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try {
            Expertise especialidadeRemovida = especialidadeService.removerFeedback(id);
            ExpertiseDTO dtoRetorno = especialidadeRemovida.toExpertiseDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") Long id){
        try {
            Expertise especialidadeBuscada = especialidadeService.buscarPorId(id);
            ExpertiseDTO dtoRetorno = especialidadeBuscada.toExpertiseDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/filtro")
    public ResponseEntity<?> buscar(@RequestBody ExpertiseDTO dto){
        try{
            Expertise filtro = dto.toExpertise();
            List<Expertise> especialidades = especialidadeService.buscar(filtro);
            List<ExpertiseDTO> dtos = especialidades
                    .stream()
                    .map(Expertise::toExpertiseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<?> buscarTodos(){
        try{
            List<Expertise> especialidadeList = especialidadeService.buscarTodos();
            List<ExpertiseDTO> dtos = especialidadeList
                    .stream()
                    .map(Expertise::toExpertiseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
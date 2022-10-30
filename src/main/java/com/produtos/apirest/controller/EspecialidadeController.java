package com.produtos.apirest.controller;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.EspecialidadeDTO;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.service.AreaService;
import com.produtos.apirest.service.EspecialidadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> salvar(@RequestBody EspecialidadeDTO dto){
        try {
            Area areaBuscada = areaService.buscarPorId(dto.getIdArea());
            Especialidade especialidade = dto.toEspecialidade(areaBuscada);
            Especialidade especialidadeSalva = especialidadeService.salvar(especialidade);
            EspecialidadeDTO dtoRetorno = especialidadeSalva.toEspecialidadeDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody EspecialidadeDTO dto){
        try{
            Area areaBuscada = areaService.buscarPorId(dto.getIdArea());
            Especialidade especialidade = dto.toEspecialidade(areaBuscada);
            Especialidade especialidadeAtualizada = especialidadeService.atualizar(especialidade);
            EspecialidadeDTO dtoRetorno = especialidadeAtualizada.toEspecialidadeDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id") Long id){
        try {
            Especialidade especialidadeBuscada = especialidadeService.buscarPorId(id);
            especialidadeService.remover(especialidadeBuscada);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try {
            Especialidade especialidadeRemovida = especialidadeService.removerFeedback(id);
            EspecialidadeDTO dtoRetorno = especialidadeRemovida.toEspecialidadeDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") Long id){
        try {
            Especialidade especialidadeBuscada = especialidadeService.buscarPorId(id);
            EspecialidadeDTO dtoRetorno = especialidadeBuscada.toEspecialidadeDTO();
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
                    .map(Especialidade::toEspecialidadeDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<?> buscarTodos(){
        try{
            List<Especialidade> especialidadeList = especialidadeService.buscarTodos();
            List<EspecialidadeDTO> dtos = especialidadeList
                    .stream()
                    .map(Especialidade::toEspecialidadeDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
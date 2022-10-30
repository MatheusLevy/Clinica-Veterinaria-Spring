package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.TipoConsultaDTO;
import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.service.TipoConsultaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipoConsulta")
public class TipoConsultaController {

    private final TipoConsultaService tipoService;

    public TipoConsultaController(TipoConsultaService tipoService) {
        this.tipoService = tipoService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody TipoConsultaDTO dto){
        try{
            TipoConsulta tipoConsulta = dto.toTipoConsulta();
            TipoConsulta tipoSalvo = tipoService.salvar(tipoConsulta);
            TipoConsultaDTO dtoRetorno = tipoSalvo.toTipoConsultaDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removerComId(@PathVariable(value = "id") Long id){
        try {
            tipoService.removerPorId(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try{
            TipoConsulta tipoRemovido = tipoService.removerComFeedback(id);
            TipoConsultaDTO dtoRetorno = tipoRemovido.toTipoConsultaDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") Long id){
        try{
            TipoConsulta tipoBuscado = tipoService.buscarPorId(id);
            TipoConsultaDTO dtoRetorno = tipoBuscado.toTipoConsultaDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody TipoConsultaDTO dto){
        try{
            TipoConsulta tipoConsulta = dto.toTipoConsulta();
            TipoConsulta tipoConsultaAtualizado = tipoService.atualizar(tipoConsulta);
            TipoConsultaDTO dtoRetorno = tipoConsultaAtualizado.toTipoConsultaDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.TipoConsultaDTO;
import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.service.TipoConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipoConsulta")
public class TipoConsultaController {

    @Autowired
    public TipoConsultaService tipoService;

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody TipoConsultaDTO dto){
        try{
            TipoConsulta tipoConsulta = dto.toTipoConsulta();
            TipoConsulta tipoSalvo = tipoService.salvar(tipoConsulta);
            TipoConsultaDTO dtoRetorno = tipoSalvo.toDTO();
            return new ResponseEntity(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity removerComId(@PathVariable(value = "id", required = true) Long id){
        try {
            tipoService.removerPorId(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity removerComFeedback(@PathVariable(value = "id", required = true) Long id){
        try{
            TipoConsulta tipoRemovido = tipoService.removerComFeedback(id);
            TipoConsultaDTO dtoRetorno = tipoRemovido.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try{
            TipoConsulta tipoBuscado = tipoService.buscarPorId(id);
            TipoConsultaDTO dtoRetorno = tipoBuscado.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody TipoConsultaDTO dto){
        try{
            TipoConsulta tipoConsulta = dto.toTipoConsulta();
            TipoConsulta tipoConsultaAtualizado = tipoService.atualizar(tipoConsulta);
            TipoConsultaDTO dtoRetorno = tipoConsultaAtualizado.toDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.TipoConsultaDTO;
import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.service.TipoConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipoconsulta")
public class TipoConsultaController {

    @Autowired
    public TipoConsultaService tipoService;

    @PreAuthorize("hasRole('A')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody TipoConsultaDTO dto){
        try{
            TipoConsulta tipoConsulta = TipoConsulta.builder()
                    .nome(dto.getNome())
                    .build();
            TipoConsulta tipoSalvo = tipoService.salvar(tipoConsulta);

            TipoConsultaDTO dtoRetorno = TipoConsultaDTO.builder()
                    .id(tipoSalvo.getTipoConsultaId())
                    .nome(tipoSalvo.getNome())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity removerComId(@PathVariable(value = "id", required = true) Long id){
        try {
            TipoConsulta tipoBuscado = tipoService.buscarTipoConsultaPorId(id);
            tipoService.remover(tipoBuscado);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity removerComFeedback(@PathVariable(value = "id", required = true) Long id){
        try{
            TipoConsulta tipoRemovido = tipoService.removerFeedback(id);
            TipoConsultaDTO dtoRetorno = TipoConsultaDTO.builder()
                    .id(tipoRemovido.getTipoConsultaId())
                    .nome(tipoRemovido.getNome())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try{
            TipoConsulta tipoBuscado = tipoService.buscarTipoConsultaPorId(id);
            TipoConsultaDTO dtoRetorno = TipoConsultaDTO.builder()
                    .id(tipoBuscado.getTipoConsultaId())
                    .nome(tipoBuscado.getNome())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody TipoConsultaDTO dto){
        try{
            TipoConsulta tipoConsulta = TipoConsulta.builder()
                    .tipoConsultaId(dto.getId())
                    .nome(dto.getNome())
                    .build();
            TipoConsulta atualizado = tipoService.atualizar(tipoConsulta);

            TipoConsultaDTO dtoRetorno = TipoConsultaDTO.builder()
                    .id(atualizado.getTipoConsultaId())
                    .nome(atualizado.getNome())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

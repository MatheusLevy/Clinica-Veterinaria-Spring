package com.produtos.apirest.controller;

import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.service.Tipo_ConsultaService;
import com.produtos.apirest.service.Tipo_animalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipoconsulta")
public class TipoConsultaController {

    @Autowired
    public Tipo_ConsultaService tipoService;


    //TODO: ### ** Substituir TipoConsulta ppro TipoConsultaDTO **
    @PreAuthorize("hasRole('A')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody TipoConsulta tipo){
        try{
            TipoConsulta tipoSalvo = tipoService.salvar(tipo);
            return ResponseEntity.ok(tipoSalvo);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity removerComId(@PathVariable(value = "id", required = true) Long id){
        try {
            TipoConsulta tipo = TipoConsulta.builder().tipoConsultaId(id).build();
            TipoConsulta tipoBuscado = tipoService.buscarTipo_consultaPorId(tipo);
            tipoService.remover(tipoBuscado);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: ### ** Substituir TipoConsulta ppro TipoConsultaDTO **
    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/feedback")
    public ResponseEntity removerComFeedback(@RequestBody TipoConsulta tipo){
        try{
            TipoConsulta tipoRemovido = tipoService.removerFeedback(tipo);
            return ResponseEntity.ok(tipoRemovido);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try{
            TipoConsulta tipo = TipoConsulta.builder().tipoConsultaId(id).build();
            TipoConsulta tipoBuscado = tipoService.buscarTipo_consultaPorId(tipo);
            return ResponseEntity.ok(tipoBuscado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //TODO: ### ** Substituir TipoConsulta ppro TipoConsultaDTO **
    @PreAuthorize("hasRole('A')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody TipoConsulta tipo){
        try{
            TipoConsulta atualizado = tipoService.atualizar(tipo);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

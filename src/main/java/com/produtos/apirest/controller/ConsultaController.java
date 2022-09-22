package com.produtos.apirest.controller;

import com.produtos.apirest.models.Consulta;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.service.ConsultaService;
import com.produtos.apirest.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consulta")
public class ConsultaController {

    @Autowired
    public ConsultaService consultaService;

    @Autowired
    public VeterinarioService veterinarioService;

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody Consulta consulta){
        try {
            Consulta consultaSalva = consultaService.salvar(consulta);
            return ResponseEntity.ok(consultaSalva);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody Consulta consulta){
        try {
            Consulta consultaAtualizada = consultaService.atualizar(consulta);
            return ResponseEntity.ok(consultaAtualizada);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity removerPorId(@PathVariable(value = "id", required = true) Long id){
        try{
            Consulta consulta = Consulta.builder().consultaId(id).build();
            Consulta consultaBuscada = consultaService.buscarComId(consulta);
            consultaService.remover(consulta);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback")
    public ResponseEntity removerComFeedback(@RequestBody Consulta consulta){
        try{
            Consulta consultaRemovida = consultaService.removerFeedback(consulta);
            return ResponseEntity.ok(consultaRemovida);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

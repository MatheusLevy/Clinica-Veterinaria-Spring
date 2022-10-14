package com.produtos.apirest.controller;

import com.produtos.apirest.models.Consulta;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.service.ConsultaService;
import com.produtos.apirest.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/api/consulta")
public class ConsultaController {

    @Autowired
    public ConsultaService consultaService;

    @Autowired
    public VeterinarioService veterinarioService;

    //TODO: ### ** Substituir Consulta por ConsultaDTO **
    @PreAuthorize("hasRole('S')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody Consulta consulta){
        try {
            Consulta consultaSalva = consultaService.salvar(consulta);
            return ResponseEntity.ok(consultaSalva);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: ### ** Substituir Consulta por ConsultaDTO **
    @PreAuthorize("hasRole('S')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody Consulta consulta){
        try {
            Consulta consultaAtualizada = consultaService.atualizar(consulta);
            return ResponseEntity.ok(consultaAtualizada);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscarTodos")
    public ResponseEntity buscarTodos(){
        try{
            List<Consulta> consultas = consultaService.buscarTodos();
            return ResponseEntity.ok(consultas);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity removerPorId(@PathVariable(value = "id", required = true) Long id){
        try{
            Consulta consulta = Consulta.builder().consultaId(id).build();
            Consulta consultaBuscada = consultaService.buscarComId(consulta.getConsultaId());
            consultaService.remover(consulta);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: ### ** Substituir Consulta por ConsultaDTO **
    @PreAuthorize("hasRole('S')")
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

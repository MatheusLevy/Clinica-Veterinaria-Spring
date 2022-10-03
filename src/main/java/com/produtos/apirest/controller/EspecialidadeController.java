package com.produtos.apirest.controller;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.service.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Especialidade")
public class EspecialidadeController {

    @Autowired
    public EspecialidadeService especialidadeService;

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody Especialidade especialidade){
        try {
            Especialidade especialidadeSalva = especialidadeService.salvar(especialidade);
            return ResponseEntity.ok(especialidadeSalva);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody Especialidade Especialidade){
        try{
            Especialidade especialidade = especialidadeService.atualizar(Especialidade);
            return ResponseEntity.ok(especialidade);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity remover(@PathVariable(value = "id", required = true) Long id){
        try {
            Especialidade especialidade = Especialidade.builder().especialidadeId(id).build();
            Especialidade especialidadeBuscada = especialidadeService.buscarEspecialidadePorId(especialidade);
            especialidadeService.remover(especialidadeBuscada);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback")
    public ResponseEntity removerComFeedback(@RequestBody Especialidade especialidade){
        try {
            Especialidade especialidadeRemovida = especialidadeService.removerFeedback(especialidade);
            return ResponseEntity.ok(especialidadeRemovida);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try {
            Especialidade especialidade = Especialidade.builder().especialidadeId(id).build();
            Especialidade especialidadeBuscada = especialidadeService.buscarEspecialidadePorId(especialidade);
            return ResponseEntity.ok(especialidadeBuscada);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/filtro")
    public ResponseEntity buscar(@RequestBody Especialidade filtro){
        try{
            List<Especialidade> especialidades = especialidadeService.buscar(filtro);
            return ResponseEntity.ok(especialidades);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity buscarTodos(){
        try{
            List<Especialidade> especialidadeList = especialidadeService.buscarTodos();
            return ResponseEntity.ok(especialidadeList);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

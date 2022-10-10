package com.produtos.apirest.controller;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/area")
public class AreaController {

    @Autowired
    public AreaService areaService;

    //TODO: ### ** Substituir Area por AreaDTO **
    @PreAuthorize("hasRole('A')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody Area area){
        try {
            Area areaSalva = areaService.salvar(area);
            return ResponseEntity.ok(areaSalva);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: ### ** Substituir Area por AreaDTO **
    @PreAuthorize("hasRole('A')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody Area area){
        try{
            Area areaSalva = areaService.atualizar(area);
            return ResponseEntity.ok(areaSalva);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity remover(@PathVariable(value = "id", required = true) Long id){
        try {
            Area area = Area.builder().areaId(id).build();
            Area areaBuscada = areaService.buscarAreaPorId(area);
            areaService.remover(areaBuscada);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: ### ** Substituir Area por AreaDTO **
    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/feedback")
    public ResponseEntity removerComFeedback(@RequestBody Area area){
        try {
            Area areaRemovida = areaService.removerFeedback(area);
            return ResponseEntity.ok(areaRemovida);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try {
            Area area = Area.builder().areaId(id).build();
            Area areaBuscada = areaService.buscarAreaPorId(area);
            return ResponseEntity.ok(areaBuscada);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: ### ** Substituir Area por AreaDTO **
    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscar/filtro")
    public ResponseEntity buscar(@RequestBody Area filtro){
        try{
            List<Area> areas = areaService.buscar(filtro);
            return ResponseEntity.ok(areas);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: ### ** Substituir Area por AreaDTO **
    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscarTodos")
    public ResponseEntity buscarTodos(){
        try {
            List<Area> areas = areaService.buscarTodos();
            return ResponseEntity.ok(areas);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscar/especialidades/{id}")
    public ResponseEntity buscarEspecialidades(@PathVariable(value = "id", required = true) Long id){
        try {
            Area area = Area.builder().areaId(id).build();
            Area areaBuscada = areaService.buscarAreaPorId(area);
            List<Especialidade> especialidades = areaService.buscarTodasEspecialidades(areaBuscada);
            return ResponseEntity.ok(especialidades);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

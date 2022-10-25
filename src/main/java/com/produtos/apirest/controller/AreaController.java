package com.produtos.apirest.controller;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.AreaDTO;
import com.produtos.apirest.models.DTO.EspecialidadeDTO;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/area")
public class AreaController {

    @Autowired
    public AreaService areaService;

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody AreaDTO areaDTO){
        try {
            Area area = areaDTO.toArea();
            Area areaSalva = areaService.salvar(area);
            AreaDTO areadtoRetorno = areaSalva.toDTO();
            return new ResponseEntity(areadtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody AreaDTO areadto){
        try{
            Area area = areadto.toArea();
            Area areaSalva = areaService.atualizar(area);
            AreaDTO areaDTORetorno = areaSalva.toDTO();
            return ResponseEntity.ok(areaDTORetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity remover(@PathVariable(value = "id", required = true) Long id){
        try {
            areaService.removerPorId(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity removerComFeedback(@PathVariable(value = "id", required = true) Long id){
        try {
            Area areaRemovida = areaService.removerComFeedback(id);
            AreaDTO retornoDTO = areaRemovida.toDTO();
            return ResponseEntity.ok(retornoDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarId/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try {
            Area areaBuscada = areaService.buscarPorId(id);
            AreaDTO retornoDTO = areaBuscada.toDTO();
            return ResponseEntity.ok(retornoDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/buscar/filtro")
    public ResponseEntity buscar(@RequestBody AreaDTO filtro){
        try{
            Area filtroArea = filtro.toArea();
            List<Area> areas = areaService.buscar(filtroArea);
            List<AreaDTO> dtos = areas
                    .stream()
                    .map(Area::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity buscarTodos(){
        try {
            List<Area> areas = areaService.buscarTodos();
            List<AreaDTO> dtos = areas
                    .stream()
                    .map(Area::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/especialidades/{id}")
    public ResponseEntity buscarEspecialidades(@PathVariable(value = "id", required = true) Long id){
        try {
            Area areaBuscada = areaService.buscarPorId(id);
            List<Especialidade> especialidades = areaService.buscarTodasEspecialidades(areaBuscada);
            List<EspecialidadeDTO> dtos = especialidades
                    .stream()
                    .map(Especialidade::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
package com.produtos.apirest.controller;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.AreaDTO;
import com.produtos.apirest.models.DTO.EspecialidadeDTO;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.service.AreaService;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import java.util.List;

@RestController
@RequestMapping("/api/area")
public class AreaController {

    @Autowired
    public AreaService areaService;

    ModelMapper modelMapper = new ModelMapper();

    @PreAuthorize("hasRole('A')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody AreaDTO areaDTO){
        try {
            Area area = Area.builder()
                    .nome(areaDTO.getNome())
                    .build();

            Area areaSalva = areaService.salvar(area);

            AreaDTO areadtoRetorno = AreaDTO.builder()
                    .id(areaSalva.getAreaId())
                    .nome(areaSalva.getNome())
                    .build();
            return new ResponseEntity(areadtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody AreaDTO areadto){
        try{
            Area area = Area.builder()
                    .areaId(areadto.getId())
                    .nome(areadto.getNome())
                    .build();
            Area areaSalva = areaService.atualizar(area);

            AreaDTO areadtoRetorno = AreaDTO.builder()
                    .id(areaSalva.getAreaId())
                    .nome(areaSalva.getNome())
                    .build();

            return ResponseEntity.ok(areadtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity remover(@PathVariable(value = "id", required = true) Long id){
        try {
            areaService.remover(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity removerComFeedback(@PathVariable(value = "id", required = true) Long id){
        try {
            Area areaRemovida = areaService.removerFeedback(id);
            AreaDTO areadto = AreaDTO.builder()
                    .id(areaRemovida.getAreaId())
                    .nome(areaRemovida.getNome())
                    .build();

            return ResponseEntity.ok(areadto);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscarId/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try {
            Area areaBuscada = areaService.buscarAreaPorId(id);
            AreaDTO areaDTO = AreaDTO.builder()
                    .id(areaBuscada.getAreaId())
                    .nome(areaBuscada.getNome())
                    .build();

            return ResponseEntity.ok(areaDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscar/filtro")
    public ResponseEntity buscar(@RequestBody AreaDTO filtro){
        try{
            Area area = Area.builder()
                    .areaId(filtro.getId())
                    .nome(filtro.getNome())
                    .build();

            List<Area> areas = areaService.buscar(area);
            List<AreaDTO> dtos = modelMapper.map(areas, new TypeToken<List<AreaDTO>>(){}.getType());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscarTodos")
    public ResponseEntity buscarTodos(){
        try {
            List<Area> areas = areaService.buscarTodos();
            List<AreaDTO> dtos = modelMapper.map(areas, new TypeToken<List<AreaDTO>>(){}.getType());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscar/especialidades/{id}")
    public ResponseEntity buscarEspecialidades(@PathVariable(value = "id", required = true) Long id){
        try {
            Area areaBuscada = areaService.buscarAreaPorId(id);
            List<Especialidade> especialidades = areaService.buscarTodasEspecialidades(areaBuscada);
            List<EspecialidadeDTO> dtos = modelMapper.map(especialidades, new TypeToken<List<EspecialidadeDTO>>(){}.getType());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

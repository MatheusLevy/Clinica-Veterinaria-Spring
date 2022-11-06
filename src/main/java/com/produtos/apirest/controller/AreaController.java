package com.produtos.apirest.controller;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.AreaDTO;
import com.produtos.apirest.models.DTO.ExpertiseDTO;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.service.AreaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/area")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService){
        this.areaService = areaService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AreaDTO areaDTO){
        try {
            Area area = areaDTO.toArea();
            Area areaSaved = areaService.save(area);
            AreaDTO dtoResponse = areaSaved.toAreaDTO();
            return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AreaDTO areadto){
        try{
            Area area = areadto.toArea();
            Area areaSaved = areaService.update(area);
            AreaDTO dtoResponse = areaSaved.toAreaDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable(value = "id") Long id){
        try {
            areaService.removeById(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id){
        try {
            Area areaRemoved = areaService.removeByIdWithFeedback(id);
            AreaDTO dtoResponse = areaRemoved.toAreaDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        try {
            Area areaFind = areaService.findById(id);
            AreaDTO dtoResponse = areaFind.toAreaDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/filter")
    public ResponseEntity<?> find(@RequestBody AreaDTO filtro){
        try{
            Area filter = filtro.toArea();
            List<Area> areas = areaService.find(filter);
            List<AreaDTO> dtosResponse = areas
                    .stream()
                    .map(Area::toAreaDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtosResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        try {
            List<Area> areas = areaService.findAll();
            List<AreaDTO> dtosResponse = areas
                    .stream()
                    .map(Area::toAreaDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtosResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/expertises/{id}")
    public ResponseEntity<?> findExpertises(@PathVariable(value = "id") Long id){
        try {
            Area areaFind = areaService.findById(id);
            List<Expertise> expertises = areaService.findAllExpertiseByAreaId(areaFind.getAreaId());
            List<ExpertiseDTO> dtosResponse = expertises
                    .stream()
                    .map(Expertise::toExpertiseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtosResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
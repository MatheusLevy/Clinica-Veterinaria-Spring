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
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AreaDTO save(@RequestBody AreaDTO areaDTO){
        Area area = areaDTO.toArea();
        Area areaSaved = areaService.save(area);
        return areaSaved.toAreaDTO();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AreaDTO areadto){
        Area area = areadto.toArea();
        Area areaSaved = areaService.update(area);
        AreaDTO dtoResponse = areaSaved.toAreaDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable(value = "id") Long id){
        areaService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id){
        Area areaRemoved = areaService.removeByIdWithFeedback(id);
        AreaDTO dtoResponse = areaRemoved.toAreaDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        Area areaFind = areaService.findById(id);
        AreaDTO dtoResponse = areaFind.toAreaDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> find(@RequestBody AreaDTO filtro){
        Area filter = filtro.toArea();
        List<Area> areas = areaService.find(filter);
        List<AreaDTO> dtosResponse = areas
                .stream()
                .map(Area::toAreaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtosResponse);
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Area> areas = areaService.findAll();
        List<AreaDTO> dtosResponse = areas
                .stream()
                .map(Area::toAreaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtosResponse);
    }

    @GetMapping("/expertises/{id}")
    public ResponseEntity<?> findExpertises(@PathVariable(value = "id") Long id){
        Area areaFind = areaService.findById(id);
        List<Expertise> expertises = areaService.findAllExpertiseByAreaId(areaFind.getAreaId());
        List<ExpertiseDTO> dtosResponse = expertises
                .stream()
                .map(Expertise::toExpertiseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtosResponse);
    }
}
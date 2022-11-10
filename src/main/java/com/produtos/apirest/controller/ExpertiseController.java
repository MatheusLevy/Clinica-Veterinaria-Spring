package com.produtos.apirest.controller;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.ExpertiseDTO;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.service.AreaService;
import com.produtos.apirest.service.ExpertiseService;
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
@RequestMapping("/api/expertise")
public class ExpertiseController {

    private final ExpertiseService expertiseService;
    private final AreaService areaService;

    public ExpertiseController(ExpertiseService expertiseService, AreaService areaService){
        this.expertiseService = expertiseService;
        this.areaService = areaService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ExpertiseDTO save(@RequestBody ExpertiseDTO dto){
        Area areaFind = areaService.findById(dto.getAreaId());
        Expertise expertise = dto.toExpertise(areaFind);
        Expertise expertiseSaved = expertiseService.save(expertise);
        return expertiseSaved.toExpertiseDTO();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ExpertiseDTO dto){
        Area areaFind = areaService.findById(dto.getAreaId());
        Expertise expertise = dto.toExpertise(areaFind);
        Expertise expertiseUpdated = expertiseService.update(expertise);
        ExpertiseDTO dtoResponse = expertiseUpdated.toExpertiseDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable(value = "id") Long id){
        Expertise expertiseFind = expertiseService.findById(id);
        expertiseService.remove(expertiseFind);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id){
        Expertise expertiseRemoved = expertiseService.removeByIdWithFeedback(id);
        ExpertiseDTO dtoResponse = expertiseRemoved.toExpertiseDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        Expertise especialidadeBuscada = expertiseService.findById(id);
        ExpertiseDTO dtoRetorno = especialidadeBuscada.toExpertiseDTO();
        return ResponseEntity.ok(dtoRetorno);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> find(@RequestBody ExpertiseDTO dto){
        Expertise filter = dto.toExpertise();
        List<Expertise> expertisesFind = expertiseService.find(filter);
        List<ExpertiseDTO> dtosResponse = expertisesFind
                .stream()
                .map(Expertise::toExpertiseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtosResponse);
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Expertise> expertises = expertiseService.findAll();
        List<ExpertiseDTO> dtosResponse = expertises
                .stream()
                .map(Expertise::toExpertiseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtosResponse);
    }
}
package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.VeterinaryDTO;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.service.ExpertiseService;
import com.produtos.apirest.service.VeterinaryService;
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
@RequestMapping("/api/veterinary")
public class VeterinaryController {

    private final VeterinaryService veterinaryService;

    private final ExpertiseService expertiseService;

    public VeterinaryController(VeterinaryService veterinaryService, ExpertiseService expertiseService) {
        this.veterinaryService = veterinaryService;
        this.expertiseService = expertiseService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public VeterinaryDTO save(@RequestBody VeterinaryDTO dto){
        Expertise expertiseFind = expertiseService.findById(dto.getExpertiseId());
        Veterinary veterinary = dto.toVeterinary(expertiseFind);
        Veterinary veterinarySaved = veterinaryService.save(veterinary);
        return veterinarySaved.toVeterinaryDTO();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody VeterinaryDTO dto){
        Expertise expertiseFind = expertiseService.findById(dto.getExpertiseId());
        Veterinary veterinary = dto.toVeterinary(expertiseFind);
        Veterinary veterinaryUpdate = veterinaryService.update(veterinary);
        VeterinaryDTO dtoResponse = veterinaryUpdate.toVeterinaryDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable(value = "id") Long id){
        veterinaryService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id){
        Veterinary veterinaryRemoved = veterinaryService.removeByIdWithFeedback(id);
        VeterinaryDTO dtoResponse = veterinaryRemoved.toVeterinaryDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        Veterinary veterinary = veterinaryService.findById(id);
        VeterinaryDTO dtoResponse = veterinary.toVeterinaryDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Veterinary> veterinaries = veterinaryService.findAll();
        List<VeterinaryDTO> dtosResponse = veterinaries
                .stream()
                .map(Veterinary::toVeterinaryDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtosResponse);
    }
}
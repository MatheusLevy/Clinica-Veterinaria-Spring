package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.VeterinaryDTO;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.service.ExpertiseService;
import com.produtos.apirest.service.VeterinarioService;
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
@RequestMapping("/api/veterinarys")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    private final ExpertiseService expertiseService;

    public VeterinarioController(VeterinarioService veterinarioService, ExpertiseService expertiseService) {
        this.veterinarioService = veterinarioService;
        this.expertiseService = expertiseService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody VeterinaryDTO dto){
        try{
            Expertise expertiseFind = expertiseService.findById(dto.getExpertiseId());
            Veterinary veterinary = dto.toVeterinary(expertiseFind);
            Veterinary veterinarySaved = veterinarioService.save(veterinary);
            VeterinaryDTO dtoResponse = veterinarySaved.toVeterinaryDTO();
            return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> update(@RequestBody VeterinaryDTO dto){
        try{
            Expertise expertiseFind = expertiseService.findById(dto.getExpertiseId());
            Veterinary veterinary = dto.toVeterinary(expertiseFind);
            Veterinary veterinaryUpdate = veterinarioService.update(veterinary);
            VeterinaryDTO dtoResponse = veterinaryUpdate.toVeterinaryDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable(value = "id") Long id){
        try {
            veterinarioService.removeById(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id){
        try{
            Veterinary veterinaryRemoved = veterinarioService.removeByIdWithFeedback(id);
            VeterinaryDTO dtoResponse = veterinaryRemoved.toVeterinaryDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        try{
            Veterinary veterinary = veterinarioService.findById(id);
            VeterinaryDTO dtoResponse = veterinary.toVeterinaryDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<?> findAll(){
        try{
            List<Veterinary> veterinaries = veterinarioService.findAll();
            List<VeterinaryDTO> dtosResponse = veterinaries
                    .stream()
                    .map(Veterinary::toVeterinaryDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtosResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
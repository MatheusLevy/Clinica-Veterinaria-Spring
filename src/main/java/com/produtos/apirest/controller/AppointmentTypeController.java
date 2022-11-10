package com.produtos.apirest.controller;

import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.models.DTO.AppointmentTypeDTO;
import com.produtos.apirest.service.AppointmentTypeService;
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

@RestController
@RequestMapping("/api/appointmentType")
public class AppointmentTypeController {

    private final AppointmentTypeService typeService;

    public AppointmentTypeController(AppointmentTypeService typeService) {
        this.typeService = typeService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AppointmentTypeDTO save(@RequestBody AppointmentTypeDTO dto){
        AppointmentType appointmentType = dto.toAppointment();
        AppointmentType typeSaved = typeService.save(appointmentType);
        return typeSaved.toAppointmentTypeDTO();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable(value = "id") Long id){
        typeService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id){
        AppointmentType typeRemoved = typeService.removeByIdWithFeedback(id);
        AppointmentTypeDTO dtoResponse = typeRemoved.toAppointmentTypeDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        AppointmentType typeFind = typeService.findById(id);
        AppointmentTypeDTO dtoResponse = typeFind.toAppointmentTypeDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AppointmentTypeDTO dto){
        AppointmentType appointmentType = dto.toAppointment();
        AppointmentType typeUpdated = typeService.update(appointmentType);
        AppointmentTypeDTO dtoResponse = typeUpdated.toAppointmentTypeDTO();
        return ResponseEntity.ok(dtoResponse);
    }
}
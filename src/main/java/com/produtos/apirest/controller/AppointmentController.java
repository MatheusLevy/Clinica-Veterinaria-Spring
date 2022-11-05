package com.produtos.apirest.controller;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Appointment;
import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.models.DTO.AppointmentDTO;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.AppointmentService;
import com.produtos.apirest.service.AppointmentTypeService;
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
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final VeterinarioService veterinaryService;
    private final AnimalService animalService;
    private final AppointmentTypeService appointmentTypeService;

    public AppointmentController(AppointmentService appointmentService, VeterinarioService veterinaryService,
                                 AnimalService animalService, AppointmentTypeService appointmentTypeService){
        this.appointmentService = appointmentService;
        this.veterinaryService = veterinaryService;
        this.animalService = animalService;
        this.appointmentTypeService = appointmentTypeService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AppointmentDTO consultadto){
        try {
            AppointmentType appointmentTypeFind = appointmentTypeService.findById(consultadto.getAppointmentTypeId());
            Veterinary veterinaryFind = veterinaryService.findById(consultadto.getVetId());
            Animal animalFind = animalService.findById(consultadto.getAnimalId());
            Appointment appointment = consultadto.toAppointment(animalFind, veterinaryFind, appointmentTypeFind);
            Appointment appointmentSaved = appointmentService.save(appointment);
            AppointmentDTO dtoResponse = appointmentSaved.toAppointmentDTO();
            return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AppointmentDTO consultadto){
        try {
            AppointmentType appointmentTypeFind = appointmentTypeService.findById(consultadto.getAppointmentTypeId());
            Veterinary veterinaryFind = veterinaryService.findById(consultadto.getVetId());
            Animal animalFind = animalService.findById(consultadto.getAnimalId());
            Appointment appointment = consultadto.toAppointment(animalFind, veterinaryFind, appointmentTypeFind);
            Appointment appointmentUpdated = appointmentService.update(appointment);
            AppointmentDTO dtoResponse = appointmentUpdated.toAppointmentDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        try{
            List<Appointment> appointments = appointmentService.findAll();
            List<AppointmentDTO> dtosResponse = appointments
                    .stream()
                    .map(Appointment::toAppointmentDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtosResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable(value = "id") Long id){
        try{
            appointmentService.removeById(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id){
        try{
            Appointment appointmentFind = appointmentService.removeByIdWithFeedback(id);
            AppointmentDTO dtoResponse = appointmentFind.toAppointmentDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
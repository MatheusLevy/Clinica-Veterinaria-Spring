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
@RequestMapping("/api/consulta")
public class ConsultaController {

    private final AppointmentService appointmentService;
    private final VeterinarioService veterinarioService;
    private final AnimalService animalService;
    private final AppointmentTypeService appointmentTypeService;

    public ConsultaController(AppointmentService appointmentService, VeterinarioService veterinarioService,
                              AnimalService animalService, AppointmentTypeService appointmentTypeService){
        this.appointmentService = appointmentService;
        this.veterinarioService = veterinarioService;
        this.animalService = animalService;
        this.appointmentTypeService = appointmentTypeService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody AppointmentDTO consultadto){
        try {
            AppointmentType tipoConsultaEncontrada = appointmentTypeService.findById(consultadto.getAppointmentTypeId());
            Veterinary veterinarioEncontrado = veterinarioService.findById(consultadto.getVetId());
            Animal AnimalEncontrado = animalService.findById(consultadto.getAnimalId());
            Appointment consulta = consultadto.toAppointment(AnimalEncontrado, veterinarioEncontrado, tipoConsultaEncontrada);
            Appointment consultaSalva = appointmentService.save(consulta);
            AppointmentDTO dtoRetorno = consultaSalva.toAppointmentDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody AppointmentDTO consultadto){
        try {
            AppointmentType tipoConsultaEncontrada = appointmentTypeService.findById(consultadto.getAppointmentTypeId());
            Veterinary veterinarioEncontrado = veterinarioService.findById(consultadto.getVetId());
            Animal AnimalEncontrado = animalService.findById(consultadto.getAnimalId());
            Appointment consulta = consultadto.toAppointment(AnimalEncontrado, veterinarioEncontrado, tipoConsultaEncontrada);
            Appointment consultaAtualizada = appointmentService.update(consulta);
            AppointmentDTO dtoRetorno = consultaAtualizada.toAppointmentDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<?> buscarTodos(){
        try{
            List<Appointment> consultas = appointmentService.findAll();
            List<AppointmentDTO> dtos = consultas
                    .stream()
                    .map(Appointment::toAppointmentDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removerPorId(@PathVariable(value = "id") Long id){
        try{
            appointmentService.removeById(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try{
            Appointment consultaRemovida = appointmentService.removeByIdWithFeedback(id);
            AppointmentDTO dtoRetorno = consultaRemovida.toAppointmentDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
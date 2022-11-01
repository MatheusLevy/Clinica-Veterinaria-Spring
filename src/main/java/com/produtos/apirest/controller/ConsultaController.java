package com.produtos.apirest.controller;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Appointment;
import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.models.DTO.AppointmentDTO;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.ConsultaService;
import com.produtos.apirest.service.TipoConsultaService;
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

    private final ConsultaService consultaService;
    private final VeterinarioService veterinarioService;
    private final AnimalService animalService;
    private final TipoConsultaService tipoConsultaService;

    public ConsultaController(ConsultaService consultaService, VeterinarioService veterinarioService,
                              AnimalService animalService, TipoConsultaService tipoConsultaService){
        this.consultaService = consultaService;
        this.veterinarioService = veterinarioService;
        this.animalService = animalService;
        this.tipoConsultaService = tipoConsultaService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody AppointmentDTO consultadto){
        try {
            AppointmentType tipoConsultaEncontrada = tipoConsultaService.buscarPorId(consultadto.getAppointmentTypeId());
            Veterinary veterinarioEncontrado = veterinarioService.buscarPorId(consultadto.getVetId());
            Animal AnimalEncontrado = animalService.buscarPorId(consultadto.getAnimalId());
            Appointment consulta = consultadto.toAppointment(AnimalEncontrado, veterinarioEncontrado, tipoConsultaEncontrada);
            Appointment consultaSalva = consultaService.salvar(consulta);
            AppointmentDTO dtoRetorno = consultaSalva.toAppointmentDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody AppointmentDTO consultadto){
        try {
            AppointmentType tipoConsultaEncontrada = tipoConsultaService.buscarPorId(consultadto.getAppointmentTypeId());
            Veterinary veterinarioEncontrado = veterinarioService.buscarPorId(consultadto.getVetId());
            Animal AnimalEncontrado = animalService.buscarPorId(consultadto.getAnimalId());
            Appointment consulta = consultadto.toAppointment(AnimalEncontrado, veterinarioEncontrado, tipoConsultaEncontrada);
            Appointment consultaAtualizada = consultaService.atualizar(consulta);
            AppointmentDTO dtoRetorno = consultaAtualizada.toAppointmentDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<?> buscarTodos(){
        try{
            List<Appointment> consultas = consultaService.buscarTodos();
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
            consultaService.removerPorId(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try{
            Appointment consultaRemovida = consultaService.removerComFeedback(id);
            AppointmentDTO dtoRetorno = consultaRemovida.toAppointmentDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
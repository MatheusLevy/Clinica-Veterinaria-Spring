package com.produtos.apirest.controller;

import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.models.DTO.AppointmentTypeDTO;
import com.produtos.apirest.service.TipoConsultaService;
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

@RestController
@RequestMapping("/api/tipoConsulta")
public class TipoConsultaController {

    private final TipoConsultaService tipoService;

    public TipoConsultaController(TipoConsultaService tipoService) {
        this.tipoService = tipoService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody AppointmentTypeDTO dto){
        try{
            AppointmentType tipoConsulta = dto.toAppointment();
            AppointmentType tipoSalvo = tipoService.salvar(tipoConsulta);
            AppointmentTypeDTO dtoRetorno = tipoSalvo.toAppointmentTypeDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removerComId(@PathVariable(value = "id") Long id){
        try {
            tipoService.removerPorId(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity<?> removerComFeedback(@PathVariable(value = "id") Long id){
        try{
            AppointmentType tipoRemovido = tipoService.removerComFeedback(id);
            AppointmentTypeDTO dtoRetorno = tipoRemovido.toAppointmentTypeDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") Long id){
        try{
            AppointmentType tipoBuscado = tipoService.buscarPorId(id);
            AppointmentTypeDTO dtoRetorno = tipoBuscado.toAppointmentTypeDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody AppointmentTypeDTO dto){
        try{
            AppointmentType tipoConsulta = dto.toAppointment();
            AppointmentType tipoConsultaAtualizado = tipoService.atualizar(tipoConsulta);
            AppointmentTypeDTO dtoRetorno = tipoConsultaAtualizado.toAppointmentTypeDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
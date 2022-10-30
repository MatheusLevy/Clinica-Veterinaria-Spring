package com.produtos.apirest.controller;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Consulta;
import com.produtos.apirest.models.DTO.ConsultaDTO;
import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.ConsultaService;
import com.produtos.apirest.service.TipoConsultaService;
import com.produtos.apirest.service.VeterinarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> salvar(@RequestBody ConsultaDTO consultadto){
        try {
            TipoConsulta tipoConsultaEncontrada = tipoConsultaService.buscarPorId(consultadto.getTipoConsultaId());
            Veterinario veterinarioEncontrado = veterinarioService.buscarPorId(consultadto.getVeterinarioId());
            Animal AnimalEncontrado = animalService.buscarPorId(consultadto.getAnimalId());
            Consulta consulta = consultadto.toConsulta(AnimalEncontrado, veterinarioEncontrado, tipoConsultaEncontrada);
            Consulta consultaSalva = consultaService.salvar(consulta);
            ConsultaDTO dtoRetorno = consultaSalva.toConsultaDTO();
            return new ResponseEntity<>(dtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody ConsultaDTO consultadto){
        try {
            TipoConsulta tipoConsultaEncontrada = tipoConsultaService.buscarPorId(consultadto.getTipoConsultaId());
            Veterinario veterinarioEncontrado = veterinarioService.buscarPorId(consultadto.getVeterinarioId());
            Animal AnimalEncontrado = animalService.buscarPorId(consultadto.getAnimalId());
            Consulta consulta = consultadto.toConsulta(AnimalEncontrado, veterinarioEncontrado, tipoConsultaEncontrada);
            Consulta consultaAtualizada = consultaService.atualizar(consulta);
            ConsultaDTO dtoRetorno = consultaAtualizada.toConsultaDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<?> buscarTodos(){
        try{
            List<Consulta> consultas = consultaService.buscarTodos();
            List<ConsultaDTO> dtos = consultas
                    .stream()
                    .map(Consulta::toConsultaDTO)
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
            Consulta consultaRemovida = consultaService.removerComFeedback(id);
            ConsultaDTO dtoRetorno = consultaRemovida.toConsultaDTO();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consulta")
public class ConsultaController {

    @Autowired
    public ConsultaService consultaService;

    @Autowired
    public VeterinarioService veterinarioService;

    @Autowired
    public AnimalService animalService;

    @Autowired
    public TipoConsultaService tipoConsultaService;


    private static ModelMapper customModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.emptyTypeMap(Consulta.class, ConsultaDTO.class)
                .addMappings(m -> m.skip(ConsultaDTO::setVeterinario))
                .addMappings(m -> m.skip(ConsultaDTO::setVeterinarios))
                .addMappings(m -> m.skip(ConsultaDTO::setAnimal))
                .addMappings(m -> m.skip(ConsultaDTO::setTiposConsulta))
                .implicitMappings();
        return modelMapper;
    }

    ModelMapper modelMapper = customModelMapper();

    @PreAuthorize("hasRole('S')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody ConsultaDTO consultadto){
        try {
            TipoConsulta tipoCosulta = tipoConsultaService.buscarTipoConsultaPorId(consultadto.getTipoConsultaId());
            Veterinario veterinario = veterinarioService.buscarPorId(consultadto.getVeterinarioId());
            Animal animal = animalService.buscarPorId(consultadto.getIdAnimal());

            Consulta consulta = Consulta.builder()
                    .tipoConsulta(tipoCosulta)
                    .veterinario(veterinario)
                    .data(consultadto.getData())
                    .descricao(consultadto.getDescricao())
                    .animal(animal)
                    .build();
            Consulta consultaSalva = consultaService.salvar(consulta);

            ConsultaDTO consultaDTORetorno = ConsultaDTO.builder()
                    .veterinarioNome(consultaSalva.getVeterinario().getNome())
                    .tipoNome(consultaSalva.getTipoConsulta().getNome())
                    .animalNome(consultaSalva.getAnimal().getNome())
                    .descricao(consultaSalva.getDescricao())
                    .data(consultaSalva.getData())
                    .build();
            return new ResponseEntity(consultaDTORetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody ConsultaDTO consultadto){
        try {
            Consulta consulta = Consulta.builder()
                    .consultaId(consultadto.getTipoConsultaId())
                    .tipoConsulta(consultadto.getTipo())
                    .veterinario(consultadto.getVeterinario())
                    .animal(consultadto.getAnimal())
                    .data(consultadto.getData())
                    .descricao(consultadto.getDescricao())
                    .build();
            Consulta consultaAtualizada = consultaService.atualizar(consulta);

            ConsultaDTO consultaDTORetorno = ConsultaDTO.builder()
                    .id(consultaAtualizada.getConsultaId())
                    .tipo(consultaAtualizada.getTipoConsulta())
                    .veterinario(consultaAtualizada.getVeterinario())
                    .animal(consultaAtualizada.getAnimal())
                    .data(consultaAtualizada.getData())
                    .descricao(consultaAtualizada.getDescricao())
                    .build();

            return ResponseEntity.ok(consultaDTORetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscarTodos")
    public ResponseEntity buscarTodos(){
        try{
            List<Consulta> consultas = consultaService.buscarTodos();
            List<ConsultaDTO> dtos = consultas
                    .stream()
                    .map(consulta -> modelMapper.map(consulta, ConsultaDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity removerPorId(@PathVariable(value = "id", required = true) Long id){
        try{
            consultaService.removerPorId(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity removerComFeedback(@PathVariable(value = "id", required = true) Long id){
        try{
            Consulta consultaRemovida = consultaService.removerFeedback(id);
            ConsultaDTO dto = ConsultaDTO.builder()
                    .id(consultaRemovida.getConsultaId())
                    .veterinarioNome(consultaRemovida.getVeterinario().getNome())
                    .animalNome(consultaRemovida.getAnimal().getNome())
                    .tipo(consultaRemovida.getTipoConsulta())
                    .data(consultaRemovida.getData())
                    .descricao(consultaRemovida.getDescricao())
                    .build();
            return ResponseEntity.ok(dto);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

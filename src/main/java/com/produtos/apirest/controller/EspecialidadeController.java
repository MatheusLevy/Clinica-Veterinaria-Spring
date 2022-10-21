package com.produtos.apirest.controller;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.EspecialidadeDTO;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.service.AreaService;
import com.produtos.apirest.service.EspecialidadeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/especialidade")
public class EspecialidadeController {

    @Autowired
    public EspecialidadeService especialidadeService;

    @Autowired
    public AreaService areaService;

    private static ModelMapper customModelMapperEspecialidade(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.emptyTypeMap(Especialidade.class, EspecialidadeDTO.class)
                .addMappings(m -> m.skip(EspecialidadeDTO::setAreas))
                .implicitMappings();
        return modelMapper;
    }

    ModelMapper modelMapperEspecialidade = customModelMapperEspecialidade();
    @PreAuthorize("hasRole('A')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody EspecialidadeDTO dto){
        try {
            Area areaBuscada = areaService.buscarPorId(dto.getIdArea());

            Especialidade especialidade = Especialidade.builder()
                    .nome(dto.getNome())
                    .area(areaBuscada)
                    .build();
            Especialidade especialidadeSalva = especialidadeService.salvar(especialidade);

            EspecialidadeDTO especialidadedtoRetorno = EspecialidadeDTO.builder()
                    .id(especialidadeSalva.getEspecialidadeId())
                    .idArea(especialidadeSalva.getArea().getAreaId())
                    .nome(especialidadeSalva.getNome())
                    .build();
            return new ResponseEntity(especialidadeSalva, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody EspecialidadeDTO dto){
        try{
            Especialidade especialidade = Especialidade.builder()
                    .especialidadeId(dto.getId())
                    .area(dto.getArea())
                    .nome(dto.getNome())
                    .build();
            Especialidade especialidadeRetorno = especialidadeService.atualizar(especialidade);

            EspecialidadeDTO especialidadedtoRetorono = EspecialidadeDTO.builder()
                    .id(especialidadeRetorno.getEspecialidadeId())
                    .nome(especialidadeRetorno.getNome())
                    .idArea(especialidadeRetorno.getArea().getAreaId())
                    .build();
            return ResponseEntity.ok(especialidadedtoRetorono);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity remover(@PathVariable(value = "id", required = true) Long id){
        try {
            Especialidade especialidadeBuscada = especialidadeService.buscarPorId(id);
            especialidadeService.remover(especialidadeBuscada);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity removerComFeedback(@PathVariable(value = "id", required = true) Long id){
        try {
            Especialidade especialidadeRemovida = especialidadeService.removerFeedback(id);
            EspecialidadeDTO especialidadedtoRetorno = EspecialidadeDTO.builder()
                    .id(especialidadeRemovida.getEspecialidadeId())
                    .idArea(especialidadeRemovida.getArea().getAreaId())
                    .nome(especialidadeRemovida.getNome())
                    .build();

            return ResponseEntity.ok(especialidadedtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try {
            Especialidade especialidadeBuscada = especialidadeService.buscarPorId(id);

            EspecialidadeDTO especialidadeDTO = EspecialidadeDTO.builder()
                    .nome(especialidadeBuscada.getNome())
                    .id(especialidadeBuscada.getEspecialidadeId())
                    .idArea(especialidadeBuscada.getArea().getAreaId())
                    .build();

            return ResponseEntity.ok(especialidadeDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscar/filtro")
    public ResponseEntity buscar(@RequestBody EspecialidadeDTO dto){
        try{
            Especialidade filtro = Especialidade.builder()
                    .especialidadeId(dto.getId())
                    .area(dto.getArea())
                    .nome(dto.getNome())
                    .build();
            List<Especialidade> especialidades = especialidadeService.buscar(filtro);
            List<EspecialidadeDTO> dtos = especialidades
                    .stream()
                    .map(especialidade -> modelMapperEspecialidade.map(especialidade, EspecialidadeDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/buscarTodos")
    public ResponseEntity buscarTodos(){
        try{
            List<Especialidade> especialidadeList = especialidadeService.buscarTodos();
            List<EspecialidadeDTO> dtos = especialidadeList
                    .stream()
                    .map(especialidade -> modelMapperEspecialidade.map(especialidade, EspecialidadeDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

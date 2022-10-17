package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.EspecialidadeDTO;
import com.produtos.apirest.models.DTO.VeterinarioDTO;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.service.EspecialidadeService;
import com.produtos.apirest.service.VeterinarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/veterinario")
public class VeterinarioController {

    @Autowired
    public VeterinarioService veterinarioService;

    @Autowired
    public EspecialidadeService especialidadeService;
    private static ModelMapper customModelMapperVeterinario(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.emptyTypeMap(Veterinario.class, VeterinarioDTO.class)
                .addMappings(m -> m.skip(VeterinarioDTO::setCpf))
                .implicitMappings();
        return modelMapper;
    }

    ModelMapper modelMapperEspecialidade = customModelMapperVeterinario();
    @PreAuthorize("hasRole('S')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody VeterinarioDTO dto){
        try{
            Veterinario veterinario = Veterinario.builder()
                    .nome(dto.getNome())
                    .telefone(dto.getTelefone())
                    .cpf(dto.getCpf())
                    .especialidade(dto.getEspecialidade())
                    .build();
            Veterinario vetSalvo = veterinarioService.salvar(veterinario);

            VeterinarioDTO dtoRetorno = VeterinarioDTO.builder()
                    .id(vetSalvo.getVeterinarioId())
                    .nome(vetSalvo.getNome())
                    .telefone(vetSalvo.getTelefone())
                    .cpf(vetSalvo.getCpf())
                    .especialidade(vetSalvo.getEspecialidade())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity removerComId(@PathVariable(value = "id", required = true) Long id){
        try {
            veterinarioService.remover(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity removerComFeedback(@PathVariable(value = "id", required = true) Long id){
        try{
            Veterinario vetRemovido = veterinarioService.removerComFeedback(id);

            VeterinarioDTO dtoRetorno = VeterinarioDTO.builder()
                    .id(vetRemovido.getVeterinarioId())
                    .nome(vetRemovido.getNome())
                    .telefone(vetRemovido.getTelefone())
                    .cpf(vetRemovido.getCpf())
                    .especialidade(vetRemovido.getEspecialidade())
                    .build();

            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try{
            Veterinario vetBuscado = veterinarioService.buscarPorId(id);

            VeterinarioDTO dtoRetorno = VeterinarioDTO.builder()
                    .id(vetBuscado.getVeterinarioId())
                    .nome(vetBuscado.getNome())
                    .telefone(vetBuscado.getTelefone())
                    .cpf(vetBuscado.getCpf())
                    .especialidade(vetBuscado.getEspecialidade())
                    .build();
            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscarTodos")
    public ResponseEntity buscarTodos(){
        try{
            List<Veterinario> veterinarioList = veterinarioService.buscarTodos();
            List<VeterinarioDTO> dtos = veterinarioList
                    .stream()
                    .map(veterinario -> modelMapperEspecialidade.map(veterinario, VeterinarioDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody VeterinarioDTO dto){
        try{
            Veterinario veterinario = Veterinario.builder()
                    .veterinarioId(dto.getId())
                    .telefone(dto.getNome())
                    .cpf(dto.getCpf())
                    .nome(dto.getNome())
                    .especialidade(dto.getEspecialidade())
                    .build();

            Veterinario atualizado = veterinarioService.atualizar(veterinario);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}

package com.produtos.apirest.controller;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Consulta;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.DTO.ConsultaDTO;
import com.produtos.apirest.models.DTO.DonoDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.service.DonoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.security.interfaces.RSAKey;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dono")
public class DonoController {

    @Autowired
    public DonoService donoService;

    private static ModelMapper customModelMapperDono(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.emptyTypeMap(Dono.class, DonoDTO.class)
                .addMappings(m -> m.skip(DonoDTO::setDonosList))
                .implicitMappings();
        return modelMapper;
    }

    ModelMapper modelMapperDono = customModelMapperDono();

    private static ModelMapper customModelMapperAnimal(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.emptyTypeMap(Animal.class, AnimalDTO.class)
                .addMappings(m -> m.skip(AnimalDTO::setDonos))
                .addMappings(m -> m.skip(AnimalDTO::setDono))
                .addMappings(m -> m.skip(AnimalDTO::setTipos))
                .addMappings(m -> m.skip(AnimalDTO::setTipo))
                .implicitMappings();
        return modelMapper;
    }

    ModelMapper modelMapperAnimal = customModelMapperAnimal();

    @PreAuthorize("hasRole('S')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody DonoDTO donodto){
        try{
            Dono dono = Dono.builder()
                    .nome(donodto.getNome())
                    .cpf(donodto.getCpf())
                    .telefone(donodto.getTelefone())
                    .build();

            Dono donoSalvo = donoService.salvar(dono);

            DonoDTO donodtoRetorno = DonoDTO.builder()
                    .id(donoSalvo.getDonoId())
                    .nome(donoSalvo.getNome())
                    .cpf(donoSalvo.getCpf())
                    .telefone(donoSalvo.getTelefone())
                    .build();

            return new ResponseEntity(donodtoRetorno, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody DonoDTO donodto){
        try{
            Dono dono = Dono.builder()
                    .donoId(donodto.getId())
                    .nome(donodto.getNome())
                    .telefone(donodto.getTelefone())
                    .cpf(donodto.getCpf())
                    .build();

            Dono donoAtualizado = donoService.atualizar(dono);

            DonoDTO donodtoRetorno = DonoDTO.builder()
                    .id(donoAtualizado.getDonoId())
                    .nome(donoAtualizado.getNome())
                    .cpf(donoAtualizado.getCpf())
                    .telefone(donoAtualizado.getTelefone())
                    .build();

            return ResponseEntity.ok(donodtoRetorno);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity remover(@PathVariable(value = "id", required = true) Long id){
        try{
            donoService.removerPorId(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscarTodos")
    public ResponseEntity buscarTodos(){

        List<Dono> donos = donoService.buscarTodos();
        List<DonoDTO> donosDTOS = donos
                .stream()
                .map(dono -> modelMapperDono.map(dono, DonoDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(donosDTOS);
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try{
            Dono donoBuscado = donoService.buscarDonoPorId(id);
            DonoDTO donodtoRetorno = DonoDTO.builder()
                    .id(donoBuscado.getDonoId())
                    .cpf(donoBuscado.getCpf())
                    .telefone(donoBuscado.getTelefone())
                    .build();
            return ResponseEntity.ok(donodtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @DeleteMapping("remover/feedback/{id}")
    public ResponseEntity removerComFeedback(@PathVariable(value = "id", required = true) Long id){
        try{
            Dono removido = donoService.removerFeedback(id);
            DonoDTO donodtoRetorno = DonoDTO.builder()
                    .id(removido.getDonoId())
                    .nome(removido.getNome())
                    .cpf(removido.getCpf())
                    .telefone(removido.getTelefone())
                    .build();
            return  ResponseEntity.ok(donodtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscar/filtro")
    public ResponseEntity buscarUtilizandoFiltro(@RequestBody DonoDTO dto){
        try{
            Dono filtro = Dono.builder()
                    .donoId(dto.getId())
                    .cpf(dto.getCpf())
                    .telefone(dto.getTelefone())
                    .nome(dto.getNome())
                    .build();
            List<Dono> buscado = donoService.buscar(filtro);
            return ResponseEntity.ok(buscado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @GetMapping("/animais/{id}")
    public ResponseEntity buscarTodosAnimais(@PathVariable(value = "id", required = true) Long id){
        try{
            List<Animal> animais  =  donoService.buscarTodosAnimais(id);
            List<AnimalDTO> dtos = animais
                    .stream()
                    .map(animal -> modelMapperAnimal.map(animal, AnimalDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

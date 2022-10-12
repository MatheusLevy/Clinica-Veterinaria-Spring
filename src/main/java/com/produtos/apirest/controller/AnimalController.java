package com.produtos.apirest.controller;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.DTO.DonoDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.DonoService;
import com.produtos.apirest.service.TipoAnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animal")
public class AnimalController {

    @Autowired
    public AnimalService animalService;

    @Autowired
    public DonoService donoService;

    @Autowired
    public TipoAnimalService tipoAnimalService;

    @PreAuthorize("hasRole('S')")
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody AnimalDTO animaldto){
        try{
            Dono dono = donoService.buscarDonoPorId(
                    Dono.builder().
                            donoId(animaldto.getIdDono())
                            .build()
            );
            TipoAnimal tipo = tipoAnimalService.buscarTipo_animalPorId(
                    TipoAnimal.builder()
                            .tipoAnimalId(animaldto.getIdTipoAnimal())
                            .build());
            Animal animal = Animal.builder()
                    .nome(animaldto.getNome())
                    .tipoAnimal(tipo)
                    .dono(dono).
                    build();
            Animal animalSalvo = animalService.salvar(animal);

            AnimalDTO animalSalvoDTO = AnimalDTO.builder()
                    .id(animalSalvo.getAnimalId())
                    .nome(animalSalvo.getNome())
                    .nomeDono(animalSalvo.getDono().getNome())
                    .nomeTipoAnimal(animalSalvo.getTipoAnimal().getNome())
                    .build();

            return new ResponseEntity(animalSalvoDTO, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity remover(@PathVariable(value = "id", required = true) Long id){
        try{
            animalService.removerPorId(id);

            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @DeleteMapping("/remover/feedback/{id}")
    public ResponseEntity removerComFeedback(@PathVariable(value = "id", required = true) Long id){
        try{

            Animal animalRemovido = animalService.removerFeedback(id);

            AnimalDTO dtoRetorno = AnimalDTO.builder()
                    .id(animalRemovido.getAnimalId())
                    .nome(animalRemovido.getNome())
                    .tipo(animalRemovido.getTipoAnimal())
                    .dono(animalRemovido.getDono())
                    .build();

            return ResponseEntity.ok(dtoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscarId/{id}")
    public ResponseEntity buscarPorId(@PathVariable(value = "id", required = true) Long id){
        try{

            Animal animalBuscado = animalService.buscarPorId(id);

            AnimalDTO animalDTO = AnimalDTO.builder()
                    .id(animalBuscado.getAnimalId())
                    .nomeTipoAnimal(animalBuscado.getTipoAnimal().getNome())
                    .nomeDono(animalBuscado.getDono().getNome())
                    .build();

        return ResponseEntity.ok(animalDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('S')")
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody  AnimalDTO animalDTO){
        try{
            Animal animal = Animal.builder()
                    .animalId(animalDTO.getId())
                    .nome(animalDTO.getNome())
                    .dono(animalDTO.getDono())
                    .tipoAnimal(animalDTO.getTipo())
                    .build();

            Animal atualizado = animalService.atualizar(animal);

            AnimalDTO atualizadoRetorno = AnimalDTO.builder()
                    .idTipoAnimal(atualizado.getAnimalId())
                    .nome(atualizado.getNome())
                    .tipo(atualizado.getTipoAnimal())
                    .nomeDono(atualizado.getDono().getNome())
                    .build();

            return ResponseEntity.ok(atualizadoRetorno);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @GetMapping("/buscarDono/{id}")
    public ResponseEntity buscarDono(@PathVariable(value = "id", required = true) Long id){
        try {
            Animal animal = Animal.builder().animalId(id).build();
            Dono dono = animalService.buscarDono(id);

            DonoDTO dto = DonoDTO.builder()
                    .id(dono.getDonoId())
                    .nome(dono.getNome())
                    .cpf(dono.getCpf())
                    .telefone(dono.getTelefone())
                    .build();

            return ResponseEntity.ok(dto);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('S')")
    @PutMapping("/atualizar/dono")
    public ResponseEntity atualizarDono(@RequestBody AnimalDTO animalDTO){
        try {
            Dono dono = Dono.builder().
                    donoId(animalDTO.getIdDono())
                    .build();
            Dono donoBuscado = donoService.buscarDonoPorId(dono);

            Animal animal = Animal.builder()
                    .animalId(animalDTO.getId())
                    .build();
            Animal animalBuscado = animalService.buscarPorId(animal.getAnimalId());

            Animal animaAtualizado = animalService.atualizarDono(donoBuscado, animalBuscado);

            AnimalDTO animalDTOAtualizado = AnimalDTO.builder()
                    .id(animaAtualizado.getAnimalId())
                    .nome(animaAtualizado.getNome())
                    .nomeDono(animaAtualizado.getDono().getNome())
                    .nomeTipoAnimal(animaAtualizado.getTipoAnimal().getNome())
                    .build();

            return ResponseEntity.ok(animalDTOAtualizado);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

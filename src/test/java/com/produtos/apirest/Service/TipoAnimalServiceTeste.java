package com.produtos.apirest.Service;

import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.repository.AnimalTypeRepo;
import com.produtos.apirest.service.TipoAnimalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TipoAnimalServiceTeste {

    @Autowired
    public TipoAnimalService tipoAnimalService;

    @Autowired
    public AnimalTypeRepo animalTypeRepo;

    protected static AnimalType generateTipoAnimal(){
        return AnimalType.builder()
                .name("test")
                .build();
    }

    private void rollback(AnimalType tipoAnimal){
        animalTypeRepo.delete(tipoAnimal);
    }

    protected void rollbackTipoAnimal(AnimalType tipoAnimal, AnimalTypeRepo animalTypeRepo){
        animalTypeRepo.delete(tipoAnimal);
    }

    @Test
    public void deveSalvar(){
        AnimalType tipoAnimalSalvo = tipoAnimalService.save(generateTipoAnimal());
        Assertions.assertNotNull(tipoAnimalSalvo);
        rollback(tipoAnimalSalvo);
    }

    @Test
    public void deveAtualizar(){
        AnimalType tipoAnimalSalvo = animalTypeRepo.save(generateTipoAnimal());
        tipoAnimalSalvo.setName("Tipo Animal Atualizado");
        AnimalType tipoAnimalAtualizado = tipoAnimalService.update(tipoAnimalSalvo);
        Assertions.assertNotNull(tipoAnimalAtualizado);
        Assertions.assertEquals(tipoAnimalSalvo.getAnimalTypeId(), tipoAnimalAtualizado.getAnimalTypeId());
        rollback(tipoAnimalAtualizado);
    }

    @Test
    public void deveRemover(){
        AnimalType tipoAnimalSalvo = animalTypeRepo.save(generateTipoAnimal());
        tipoAnimalService.remove(tipoAnimalSalvo);
        Assertions.assertFalse(animalTypeRepo.findById(tipoAnimalSalvo.getAnimalTypeId()).isPresent());
    }

    @Test
    public void deveRemoverComFeedback(){
        AnimalType tipoAnimalSalvo = animalTypeRepo.save(generateTipoAnimal());
        AnimalType tipoAnimalFeedback = tipoAnimalService.removeByIdWithFeedback(tipoAnimalSalvo.getAnimalTypeId());
        Assertions.assertNotNull(tipoAnimalFeedback);
        Assertions.assertEquals(tipoAnimalSalvo.getAnimalTypeId(), tipoAnimalFeedback.getAnimalTypeId());
    }

    @Test
    public void deveRemoverPorId(){
        AnimalType tipoAnimalSalvo = animalTypeRepo.save(generateTipoAnimal());
        Long id = tipoAnimalSalvo.getAnimalTypeId();
        tipoAnimalService.removeById(id);;
        Assertions.assertFalse(animalTypeRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscarPorId(){
        AnimalType tipoAnimalSalvo = animalTypeRepo.save(generateTipoAnimal());
        AnimalType TipoAnimalEncontrado = tipoAnimalService.findById(tipoAnimalSalvo.getAnimalTypeId());
        Assertions.assertNotNull(TipoAnimalEncontrado);
        Assertions.assertEquals(TipoAnimalEncontrado.getAnimalTypeId(), tipoAnimalSalvo.getAnimalTypeId());
        rollback(tipoAnimalSalvo);
    }

    @Test
    public void deveBuscarPorFiltro(){
        AnimalType tipoAnimalSalvo = animalTypeRepo.save(generateTipoAnimal());
        List<AnimalType> tiposEncontradosList = tipoAnimalService.find(tipoAnimalSalvo);
        Assertions.assertNotNull(tiposEncontradosList);
        Assertions.assertFalse(tiposEncontradosList.isEmpty());
        rollback(tipoAnimalSalvo);
    }

    @Test
    public void deveBuscarTodos(){
        AnimalType tipoAnimalSalvo = animalTypeRepo.save(generateTipoAnimal());
        List<AnimalType> tiposList = tipoAnimalService.findAll();
        Assertions.assertNotNull(tiposList);
        Assertions.assertFalse(tiposList.isEmpty());
        rollback(tipoAnimalSalvo);
    }
}
package com.produtos.apirest.Model;

import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.repository.AnimalTypeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TipoAnimalTeste {
    @Autowired
    public AnimalTypeRepo animalTypeRepo;

    protected static AnimalType generateTipoAnimal(){
       return AnimalType.builder()
                .name("name")
                .build();
    }

    private void rollback(AnimalType tipoAnimal){
        animalTypeRepo.delete(tipoAnimal);
    }

    protected static void rollbackTipoAnimal(AnimalType tipoAnimal, AnimalTypeRepo animalTypeRepo){
        animalTypeRepo.delete(tipoAnimal);
    }

    @Test
    public void deveSalvar(){
        AnimalType tipoAnimalSalvo = animalTypeRepo.save(generateTipoAnimal());
        Assertions.assertNotNull(tipoAnimalSalvo);
        Assertions.assertEquals(generateTipoAnimal().getName(), tipoAnimalSalvo.getName());
        rollback(tipoAnimalSalvo);
    }

    @Test
    public void deveAtualizar(){
        AnimalType tipoAnimalSalvo = animalTypeRepo.save(generateTipoAnimal());
        tipoAnimalSalvo.setName("Novo nome");
        AnimalType tipoAnimalAtualizado = animalTypeRepo.save(tipoAnimalSalvo);
        Assertions.assertNotNull(tipoAnimalAtualizado);
        Assertions.assertEquals(tipoAnimalAtualizado.getAnimalTypeId(), tipoAnimalSalvo.getAnimalTypeId());
        Assertions.assertEquals(tipoAnimalAtualizado.getName(), "Novo nome");
        rollback(tipoAnimalAtualizado);
    }

    @Test
    public void deveRemover(){
        AnimalType tipoAnimalSalvo = animalTypeRepo.save(generateTipoAnimal());
        Long id = tipoAnimalSalvo.getAnimalTypeId();
        animalTypeRepo.deleteById(id);
        Assertions.assertFalse(animalTypeRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscar(){
        AnimalType tipoAnimalSalvo = animalTypeRepo.save(generateTipoAnimal());
        Long id = tipoAnimalSalvo.getAnimalTypeId();
        Assertions.assertTrue(animalTypeRepo.findById(id).isPresent());
        rollback(tipoAnimalSalvo);
    }
}
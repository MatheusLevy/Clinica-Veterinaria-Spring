package com.produtos.apirest.Model;

import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.repository.AnimalTypeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AnimalTypeTest {
    @Autowired
    public AnimalTypeRepo animalTypeRepo;

    protected static AnimalType generateAnimalType(){
       return AnimalType.builder()
                .name("name")
                .build();
    }

    private void rollback(AnimalType animalType){
        animalTypeRepo.delete(animalType);
    }

    protected static void rollbackAnimalType(AnimalType animalType, AnimalTypeRepo animalTypeRepo){
        animalTypeRepo.delete(animalType);
    }

    @Test
    public void save(){
        AnimalType typeSaved = animalTypeRepo.save(generateAnimalType());
        Assertions.assertNotNull(typeSaved);
        Assertions.assertEquals(generateAnimalType().getName(), typeSaved.getName());
        rollback(typeSaved);
    }

    @Test
    public void update(){
        AnimalType typeSaved = animalTypeRepo.save(generateAnimalType());
        typeSaved.setName("New Name");
        AnimalType typeUpdated = animalTypeRepo.save(typeSaved);
        Assertions.assertNotNull(typeUpdated);
        Assertions.assertEquals(typeUpdated.getAnimalTypeId(), typeSaved.getAnimalTypeId());
        Assertions.assertEquals(typeUpdated.getName(), "New Name");
        rollback(typeUpdated);
    }

    @Test
    public void removeById(){
        AnimalType typeSaved = animalTypeRepo.save(generateAnimalType());
        Long id = typeSaved.getAnimalTypeId();
        animalTypeRepo.deleteById(id);
        Assertions.assertNull(animalTypeRepo.findByAnimalTypeId(id));
    }

    @Test
    public void findById(){
        AnimalType typeSaved = animalTypeRepo.save(generateAnimalType());
        Long id = typeSaved.getAnimalTypeId();
        Assertions.assertNotNull(animalTypeRepo.findByAnimalTypeId(id));
        rollback(typeSaved);
    }
}
package com.produtos.apirest.Service;

import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.repository.AnimalTypeRepo;
import com.produtos.apirest.service.AnimalTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AnimalTypeServiceTest {

    @Autowired
    public AnimalTypeService animalTypeService;

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

    protected void rollbackAnimalType(AnimalType animalType, AnimalTypeRepo animalTypeRepo){
        animalTypeRepo.delete(animalType);
    }

    @Test
    public void save(){
        AnimalType typeSaved = animalTypeService.save(generateAnimalType());
        Assertions.assertNotNull(typeSaved);
        rollback(typeSaved);
    }

    @Test
    public void update(){
        AnimalType typeSaved = animalTypeRepo.save(generateAnimalType());
        typeSaved.setName("New Name");
        AnimalType typeUpdated = animalTypeService.update(typeSaved);
        Assertions.assertNotNull(typeUpdated);
        Assertions.assertEquals(typeSaved.getAnimalTypeId(), typeUpdated.getAnimalTypeId());
        rollback(typeUpdated);
    }

    @Test
    public void remove(){
        AnimalType typeSaved = animalTypeRepo.save(generateAnimalType());
        Long id = typeSaved.getAnimalTypeId();
        animalTypeService.remove(typeSaved);
        Assertions.assertNull(animalTypeRepo.findByAnimalTypeId(id));
    }

    @Test
    public void removeByIdWithFeedback(){
        AnimalType typeSaved = animalTypeRepo.save(generateAnimalType());
        AnimalType feedback = animalTypeService.removeByIdWithFeedback(typeSaved.getAnimalTypeId());
        Assertions.assertNotNull(feedback);
        Assertions.assertEquals(typeSaved.getAnimalTypeId(), feedback.getAnimalTypeId());
    }

    @Test
    public void removeById(){
        AnimalType typeSaved = animalTypeRepo.save(generateAnimalType());
        Long id = typeSaved.getAnimalTypeId();
        animalTypeService.removeById(id);
        Assertions.assertNull(animalTypeRepo.findByAnimalTypeId(id));
    }

    @Test
    public void findById(){
        AnimalType typeSaved = animalTypeRepo.save(generateAnimalType());
        AnimalType typeFind = animalTypeService.findById(typeSaved.getAnimalTypeId());
        Assertions.assertNotNull(typeFind);
        Assertions.assertEquals(typeFind.getAnimalTypeId(), typeSaved.getAnimalTypeId());
        rollback(typeSaved);
    }

    @Test
    public void find(){
        AnimalType typeSaved = animalTypeRepo.save(generateAnimalType());
        List<AnimalType> types = animalTypeService.find(typeSaved);
        Assertions.assertNotNull(types);
        Assertions.assertFalse(types.isEmpty());
        rollback(typeSaved);
    }

    @Test
    public void findAll(){
        AnimalType typeSaved = animalTypeRepo.save(generateAnimalType());
        List<AnimalType> types = animalTypeService.findAll();
        Assertions.assertNotNull(types);
        Assertions.assertFalse(types.isEmpty());
        rollback(typeSaved);
    }
}
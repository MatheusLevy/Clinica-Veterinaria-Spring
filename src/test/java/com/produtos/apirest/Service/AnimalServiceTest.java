package com.produtos.apirest.Service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.AnimalTypeRepo;
import com.produtos.apirest.repository.OwnerRepo;
import com.produtos.apirest.service.AnimalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.produtos.apirest.Service.OwnerServiceTest.generateOwner;
import static com.produtos.apirest.Service.AnimalTypeServiceTest.generateAnimalType;

@SpringBootTest
public class AnimalServiceTest {

    @Autowired
    public AnimalService animalService;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public OwnerRepo ownerRepo;

    @Autowired
    public AnimalTypeRepo animalTypeRepo;


    protected static Animal generateAnimal(AnimalTypeRepo animalTypeRepo, OwnerRepo ownerRepo,
                                           Boolean initFields){
        Animal animal = Animal.builder()
                .name("name")
                .animalType(generateAnimalType())
                .owner(generateOwner())
                .build();
        if (initFields){
            animal.setAnimalType(animalTypeRepo.save(animal.getAnimalType()));
            animal.setOwner(ownerRepo.save(animal.getOwner()));
        }
        return animal;
    }

    private Animal generateAnimal(Boolean initFields){
        Animal animal = Animal.builder()
                .name("test")
                .animalType(generateAnimalType())
                .owner(generateOwner())
                .build();
        if (initFields){
            animal.setAnimalType(animalTypeRepo.save(animal.getAnimalType()));
            animal.setOwner(ownerRepo.save(animal.getOwner()));
        }
        return animal;
    }

    protected static void rollbackAnimal(Animal animal, AnimalRepo animalRepo,
                                         OwnerRepo ownerRepo,
                                         AnimalTypeRepo animalTypeRepo){
        animalRepo.delete(animal);
        ownerRepo.delete(animal.getOwner());
        animalTypeRepo.delete(animal.getAnimalType());
    }

    private void rollback(Animal animal, Boolean skipAnimal){
        if (!skipAnimal)
            animalRepo.delete(animal);
        ownerRepo.delete(animal.getOwner());
        animalTypeRepo.delete(animal.getAnimalType());
    }

    @Test
    public void save(){
        Animal animalSaved = animalService.save(generateAnimal(true));
        Assertions.assertNotNull(animalSaved);
        rollback(animalSaved, false);
    }

    @Test
    public void update(){
        Animal animalSaved = animalRepo.save(generateAnimal(true));
        animalSaved.setName("New Name");
        Animal animalUpdated = animalService.update(animalSaved);
        Assertions.assertNotNull(animalUpdated);
        Assertions.assertEquals(animalSaved.getAnimalId(), animalUpdated.getAnimalId());
        Assertions.assertEquals(animalUpdated.getName(), "New Name");
        rollback(animalSaved, false);
    }

    @Test
    public void remove(){
        Animal animalSaved = animalRepo.save(generateAnimal(true));
        Long id = animalSaved.getAnimalId();
        animalService.remove(animalSaved);
        Assertions.assertNull(animalRepo.findByAnimalId(id));
        rollback(animalSaved, true);
    }

    @Test
    public void removeById(){
        Animal animalSaved = animalRepo.save(generateAnimal(true));
        Long id = animalSaved.getAnimalId();
        animalService.removeById(id);
        Assertions.assertNull(animalRepo.findByAnimalId(id));
        rollback(animalSaved, true);
    }

    @Test
    public void removeByIdWithFeedback(){
        Animal animalSaved = animalRepo.save(generateAnimal(true));
        Long id = animalSaved.getAnimalId();
        animalService.removeByIdWithFeedback(id);
        Assertions.assertNull(animalRepo.findByAnimalId(id));
        rollback(animalSaved, true);
    }

    @Test
    public void findById(){
        Animal animalSaved = animalRepo.save(generateAnimal(true));
        Long id = animalSaved.getAnimalId();
        Animal animalFind = animalService.findById(id);
        Assertions.assertNotNull(animalFind);
        Assertions.assertEquals(animalSaved.getOwner().getOwnerId(), animalFind.getOwner().getOwnerId());
        rollback(animalSaved, false);
    }

    @Test
    public void find(){
        Animal animalSaved = animalRepo.save(generateAnimal(true));
        List<Animal> animals = animalService.find(animalSaved);
        Assertions.assertNotNull(animals);
        Assertions.assertFalse(animals.isEmpty());
        rollback(animalSaved, false);
    }

    @Test
    public void findAll(){
        Animal animalSaved = animalRepo.save(generateAnimal(true));
        List<Animal> animals = animalService.findAll();
        Assertions.assertNotNull(animals);
        Assertions.assertFalse(animals.isEmpty());
        rollback(animalSaved, false);
    }

    @Test
    public void findOwnerByAnimalId(){
        Animal animalSaved = animalRepo.save(generateAnimal(true));
        Owner ownerFind = animalService.findOwnerByAnimalId(animalSaved.getAnimalId());
        Assertions.assertNotNull(ownerFind);
        Assertions.assertEquals(ownerFind.getOwnerId(), animalSaved.getOwner().getOwnerId());
        rollback(animalSaved, false);
    }

    @Test
    public void updateOwner(){
        Animal animalSaved = animalRepo.save(generateAnimal(true));
        Owner ownerOld = animalSaved.getOwner();
        Owner ownerNew = ownerRepo.save(generateOwner());
        Animal animalUpdated = animalService.updateOwner(animalSaved, ownerNew);
        Assertions.assertNotNull(animalUpdated);
        Assertions.assertEquals(animalUpdated.getOwner().getOwnerId(), ownerNew.getOwnerId());
        rollback(animalUpdated, false);
        ownerRepo.delete(ownerOld);
    }
}
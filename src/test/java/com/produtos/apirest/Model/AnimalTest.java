package com.produtos.apirest.Model;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.AnimalTypeRepo;
import com.produtos.apirest.repository.OwnerRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.produtos.apirest.Model.OwnerTest.generateOwner;
import static com.produtos.apirest.Model.OwnerTest.rollbackOwner;
import static com.produtos.apirest.Model.AnimalTypeTest.generateAnimalType;
import static com.produtos.apirest.Model.AnimalTypeTest.rollbackAnimalType;

@SpringBootTest
public class AnimalTest {
    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public AnimalTypeRepo animalTypeRepo;

    @Autowired
    public OwnerRepo ownerRepo;

    protected Animal generateAnimal(){
        return Animal.builder()
                .name("name")
                .animalType(animalTypeRepo.save(generateAnimalType()))
                .owner(ownerRepo.save(generateOwner()))
                .build();
    }

    protected static Animal generateAnimal(AnimalTypeRepo animalTypeRepo, OwnerRepo ownerRepo){
        return Animal.builder()
                .name("name")
                .animalType(animalTypeRepo.save(generateAnimalType()))
                .owner(ownerRepo.save(generateOwner()))
                .build();
    }

    private void rollback(Animal animal){
        animalRepo.delete(animal);
        animalTypeRepo.delete(animal.getAnimalType());
        ownerRepo.delete(animal.getOwner());
    }

    protected static void rollbackAnimal(Animal animal, AnimalRepo animalRepo,
                                         AnimalTypeRepo animalTypeRepo, OwnerRepo ownerRepo){
        animalRepo.delete(animal);
        animalTypeRepo.delete(animal.getAnimalType());
        ownerRepo.delete(animal.getOwner());
    }

    @Test
    public void save(){
        Animal animalSaved = animalRepo.save(generateAnimal());
        Assertions.assertNotNull(animalSaved);
        rollback(animalSaved);
    }

    @Test
    public void updateOwner(){
        Animal animalSaved = animalRepo.save(generateAnimal());
        Owner ownerOld = animalSaved.getOwner();
        animalSaved.setOwner(ownerRepo.save(generateOwner()));
        Animal animalUpdated = animalRepo.save(animalSaved);
        Assertions.assertNotNull(animalUpdated);
        Assertions.assertEquals(animalSaved.getAnimalId(), animalUpdated.getAnimalId());
        rollback(animalUpdated);
        rollbackOwner(ownerOld, ownerRepo);
    }

    @Test
    public void update(){
        Animal animalSaved = animalRepo.save(generateAnimal());
        animalSaved.setName("New Name");
        Animal animalUpdated = animalRepo.save(animalSaved);
        Assertions.assertNotNull(animalUpdated);
        Assertions.assertEquals(animalUpdated.getAnimalId(), animalSaved.getAnimalId());
        Assertions.assertEquals(animalUpdated.getName(), "New Name");
        rollback(animalUpdated);
    }

    @Test
    public void updateAnimalType(){
        Animal animalSaved = animalRepo.save(generateAnimal());
        AnimalType animalTypeOld = animalSaved.getAnimalType();
        animalSaved.setAnimalType(animalTypeRepo.save(generateAnimalType()));
        Animal animalUpdated = animalRepo.save(animalSaved);
        Assertions.assertNotNull(animalUpdated);
        Assertions.assertEquals(animalUpdated.getAnimalId(), animalSaved.getAnimalId());
        Assertions.assertEquals(animalUpdated.getAnimalType().getAnimalTypeId(), animalSaved.getAnimalType().getAnimalTypeId());
        rollback(animalUpdated);
        rollbackAnimalType(animalTypeOld, animalTypeRepo);
    }

    @Test
    public void removeById(){
        Animal animalSaved = animalRepo.save(generateAnimal());
        Long id = animalSaved.getAnimalId();
        animalRepo.delete(animalSaved);
        Assertions.assertNull(animalRepo.findByAnimalId(id));
        rollbackAnimalType(animalSaved.getAnimalType(), animalTypeRepo);
        rollbackOwner(animalSaved.getOwner(), ownerRepo);

    }

    @Test
    public void findById(){
        Animal animalSaved = animalRepo.save(generateAnimal());
        Long id = animalSaved.getAnimalId();
        Assertions.assertNotNull(animalRepo.findByAnimalId(id));
        rollback(animalSaved);
    }
}
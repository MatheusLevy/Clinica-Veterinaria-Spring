package com.produtos.apirest.Service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.AnimalTypeRepo;
import com.produtos.apirest.repository.OwnerRepo;
import com.produtos.apirest.service.OwnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.produtos.apirest.Service.AnimalServiceTest.generateAnimal;
import static com.produtos.apirest.Service.AnimalServiceTest.rollbackAnimal;
import static com.produtos.apirest.Util.Util.generateCPF;
import static com.produtos.apirest.Util.Util.generatePhone;

@SpringBootTest
public class OwnerServiceTest {

    @Autowired
    public OwnerService ownerService;

    @Autowired
    public OwnerRepo ownerRepo;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public AnimalTypeRepo animalTypeRepo;

    protected static Owner generateOwner(){
        return Owner.builder()
                .name("name")
                .phone(generatePhone())
                .cpf(generateCPF())
                .build();
    }

    private void rollback(Owner dono){
        ownerRepo.delete(dono);
    }

    protected static void rollbackOwner(Owner owner, OwnerRepo ownerRepo){
        ownerRepo.delete(owner);
    }

    @Test
    public void save(){
        Owner ownerSaved = ownerService.save(generateOwner());
        Assertions.assertNotNull(ownerSaved);
        rollback(ownerSaved);
    }

    @Test
    public void update(){
        Owner ownerSaved = ownerRepo.save(generateOwner());
        ownerSaved.setName("New Name");
        Owner ownerUpdated = ownerService.update(ownerSaved);
        Assertions.assertNotNull(ownerUpdated);
        Assertions.assertEquals(ownerSaved.getOwnerId(), ownerUpdated.getOwnerId());
        rollback(ownerUpdated);
    }

    @Test
    public void remove(){
        Owner ownerSaved = ownerRepo.save(generateOwner());
        Long id = ownerSaved.getOwnerId();
        ownerService.remove(ownerSaved);
        Assertions.assertNull(ownerRepo.findByOwnerId(id));
    }

    @Test
    public void removeByIdWithFeedback(){
        Owner ownerSaved = ownerRepo.save(generateOwner());
        Owner feedback = ownerService.removeByIdWithFeedback(ownerSaved.getOwnerId());
        Assertions.assertNotNull(feedback);
        Assertions.assertEquals(feedback.getOwnerId(), ownerSaved.getOwnerId());
    }

    @Test
    public void removeById(){
        Owner ownerSaved = ownerRepo.save(generateOwner());
        Long id = ownerSaved.getOwnerId();
        ownerService.removeById(ownerSaved.getOwnerId());
        Assertions.assertNull(ownerRepo.findByOwnerId(id));
    }

    @Test
    public void findById(){
        Owner ownerSaved = ownerRepo.save(generateOwner());
        Owner ownerFind = ownerService.findById(ownerSaved.getOwnerId());
        Assertions.assertNotNull(ownerFind);
        Assertions.assertEquals(ownerSaved.getOwnerId(), ownerFind.getOwnerId());
        rollback(ownerFind);
    }

    @Test
    public void findAll(){
        Owner ownerSaved = ownerService.save(generateOwner());
        List<Owner> owners = ownerService.findAll();
        Assertions.assertNotNull(owners);
        Assertions.assertFalse(owners.isEmpty());
        rollback(ownerSaved);
    }

    @Test
    public void find(){
        Owner ownerFind = ownerRepo.save(generateOwner());
        List<Owner> owners = ownerService.find(ownerFind);
        Assertions.assertNotNull(owners);
        Assertions.assertFalse(owners.isEmpty());
        rollback(ownerFind);
    }

    @Test
    public void findAllAnimalByOwnerId(){
        Animal animal = animalRepo.save(generateAnimal(animalTypeRepo, ownerRepo, true));
        Owner OwnerOfAnimal = animal.getOwner();
        List<Animal> animals = ownerService.findAllAnimalsByOwnerId(OwnerOfAnimal.getOwnerId());
        Assertions.assertNotNull(animals);
        Assertions.assertFalse(animals.isEmpty());
        rollbackAnimal(animal, animalRepo, ownerRepo, animalTypeRepo);
    }
}
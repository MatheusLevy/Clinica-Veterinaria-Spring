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

import static com.produtos.apirest.Model.DonoTeste.generateDono;
import static com.produtos.apirest.Model.DonoTeste.rollbackDono;
import static com.produtos.apirest.Model.TipoAnimalTeste.generateTipoAnimal;
import static com.produtos.apirest.Model.TipoAnimalTeste.rollbackTipoAnimal;

@SpringBootTest
public class AnimalTeste {
    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public AnimalTypeRepo animalTypeRepo;

    @Autowired
    public OwnerRepo ownerRepo;

    protected Animal generateAnimal(){
        return Animal.builder()
                .name("name")
                .animalType(animalTypeRepo.save(generateTipoAnimal()))
                .owner(ownerRepo.save(generateDono()))
                .build();
    }

    protected static Animal generateAnimal(AnimalTypeRepo animalTypeRepo, OwnerRepo ownerRepo){
        return Animal.builder()
                .name("name")
                .animalType(animalTypeRepo.save(generateTipoAnimal()))
                .owner(ownerRepo.save(generateDono()))
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
    public void deveSalvar(){
        Animal animalSalvo = animalRepo.save(generateAnimal());
        Assertions.assertNotNull(animalSalvo);
        rollback(animalSalvo);
    }

    @Test
    public void deveAlterarDono(){
        Animal animalSalvo = animalRepo.save(generateAnimal());
        Owner donoAntigo = animalSalvo.getOwner();
        animalSalvo.setOwner(ownerRepo.save(generateDono()));
        Animal animalAtualizado = animalRepo.save(animalSalvo);
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalSalvo.getAnimalId(), animalAtualizado.getAnimalId());
        rollback(animalAtualizado);
        rollbackDono(donoAntigo, ownerRepo);
    }

    @Test
    public void deveAtualizar(){
        Animal animalSalvo = animalRepo.save(generateAnimal());
        animalSalvo.setName("Nome alterado");
        Animal animalAtualizado = animalRepo.save(animalSalvo);
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalAtualizado.getAnimalId(), animalSalvo.getAnimalId());
        Assertions.assertEquals(animalAtualizado.getName(), "Nome alterado");
        rollback(animalAtualizado);
    }

    @Test
    public void deveAtualizarTipoAnimal(){
        Animal animalSalvo = animalRepo.save(generateAnimal());
        AnimalType tipoAnimalAntigo = animalSalvo.getAnimalType();
        animalSalvo.setAnimalType(animalTypeRepo.save(generateTipoAnimal()));
        Animal animalAtualizado = animalRepo.save(animalSalvo);
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalAtualizado.getAnimalId(), animalSalvo.getAnimalId());
        Assertions.assertEquals(animalAtualizado.getAnimalType().getAnimalTypeId(), animalSalvo.getAnimalType().getAnimalTypeId());
        rollback(animalAtualizado);
        rollbackTipoAnimal(tipoAnimalAntigo, animalTypeRepo);
    }

    @Test
    public void deveRemover(){
        Animal animalSalvo = animalRepo.save(generateAnimal());
        Long id = animalSalvo.getAnimalId();
        animalRepo.delete(animalSalvo);
        Assertions.assertFalse(animalRepo.findById(id).isPresent());
        rollbackTipoAnimal(animalSalvo.getAnimalType(), animalTypeRepo);
        rollbackDono(animalSalvo.getOwner(), ownerRepo);

    }

    @Test
    public void deveBuscar(){
        Animal animalSalvo = animalRepo.save(generateAnimal());
        Long id = animalSalvo.getAnimalId();
        Assertions.assertTrue(animalRepo.findById(id).isPresent());
        rollback(animalSalvo);
    }
}
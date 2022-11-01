package com.produtos.apirest.Model;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.DonoRepo;
import com.produtos.apirest.repository.TipoAnimalRepo;
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
    public TipoAnimalRepo tipoAnimalRepo;

    @Autowired
    public DonoRepo donoRepo;

    protected Animal generateAnimal(){
        return Animal.builder()
                .name("name")
                .animalType(tipoAnimalRepo.save(generateTipoAnimal()))
                .owner(donoRepo.save(generateDono()))
                .build();
    }

    protected static Animal generateAnimal(TipoAnimalRepo tipoAnimalRepo, DonoRepo donoRepo){
        return Animal.builder()
                .name("name")
                .animalType(tipoAnimalRepo.save(generateTipoAnimal()))
                .owner(donoRepo.save(generateDono()))
                .build();
    }

    private void rollback(Animal animal){
        animalRepo.delete(animal);
        tipoAnimalRepo.delete(animal.getAnimalType());
        donoRepo.delete(animal.getOwner());
    }

    protected static void rollbackAnimal(Animal animal, AnimalRepo animalRepo,
                                         TipoAnimalRepo tipoAnimalRepo, DonoRepo donoRepo){
        animalRepo.delete(animal);
        tipoAnimalRepo.delete(animal.getAnimalType());
        donoRepo.delete(animal.getOwner());
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
        Dono donoAntigo = animalSalvo.getOwner();
        animalSalvo.setOwner(donoRepo.save(generateDono()));
        Animal animalAtualizado = animalRepo.save(animalSalvo);
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalSalvo.getAnimalId(), animalAtualizado.getAnimalId());
        rollback(animalAtualizado);
        rollbackDono(donoAntigo, donoRepo);
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
        TipoAnimal tipoAnimalAntigo = animalSalvo.getAnimalType();
        animalSalvo.setAnimalType(tipoAnimalRepo.save(generateTipoAnimal()));
        Animal animalAtualizado = animalRepo.save(animalSalvo);
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalAtualizado.getAnimalId(), animalSalvo.getAnimalId());
        Assertions.assertEquals(animalAtualizado.getAnimalType().getAnimalTypeId(), animalSalvo.getAnimalType().getAnimalTypeId());
        rollback(animalAtualizado);
        rollbackTipoAnimal(tipoAnimalAntigo, tipoAnimalRepo);
    }

    @Test
    public void deveRemover(){
        Animal animalSalvo = animalRepo.save(generateAnimal());
        Long id = animalSalvo.getAnimalId();
        animalRepo.delete(animalSalvo);
        Assertions.assertFalse(animalRepo.findById(id).isPresent());
        rollbackTipoAnimal(animalSalvo.getAnimalType(), tipoAnimalRepo);
        rollbackDono(animalSalvo.getOwner(), donoRepo);

    }

    @Test
    public void deveBuscar(){
        Animal animalSalvo = animalRepo.save(generateAnimal());
        Long id = animalSalvo.getAnimalId();
        Assertions.assertTrue(animalRepo.findById(id).isPresent());
        rollback(animalSalvo);
    }
}
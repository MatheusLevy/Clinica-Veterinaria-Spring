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

import static com.produtos.apirest.Service.OwnerServiceTeste.generateDono;
import static com.produtos.apirest.Service.AnimalTypeServiceTeste.generateTipoAnimal;

@SpringBootTest
public class AnimalServiceTeste {

    @Autowired
    public AnimalService animalService;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public OwnerRepo ownerRepo;

    @Autowired
    public AnimalTypeRepo animalTypeRepo;


    protected static Animal generateAnimal(AnimalTypeRepo animalTypeRepo, OwnerRepo ownerRepo,
                                           Boolean initializeFields){
        Animal animal = Animal.builder()
                .name("test")
                .animalType(generateTipoAnimal())
                .owner(generateDono())
                .build();
        if (initializeFields){
            animal.setAnimalType(animalTypeRepo.save(animal.getAnimalType()));
            animal.setOwner(ownerRepo.save(animal.getOwner()));
        }
        return animal;
    }

    private Animal generateAnimal(Boolean initializeFields){
        Animal animal = Animal.builder()
                .name("test")
                .animalType(generateTipoAnimal())
                .owner(generateDono())
                .build();
        if (initializeFields){
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
    public void deveSalvar(){
        Animal animalSalvo = animalService.save(generateAnimal(true));
        Assertions.assertNotNull(animalSalvo);
        rollback(animalSalvo, false);
    }

    @Test
    public void deveAtualizar(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        animalSalvo.setName("Animal Atualizado");
        Animal animalAtualizado = animalService.update(animalSalvo);
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalSalvo.getAnimalId(), animalAtualizado.getAnimalId());
        Assertions.assertEquals(animalAtualizado.getName(), "Animal Atualizado");
        rollback(animalSalvo, false);
    }

    @Test
    public void deveRemover(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Long id = animalSalvo.getAnimalId();
        animalService.remove(animalSalvo);
        Assertions.assertFalse(animalRepo.findById(id).isPresent());
        rollback(animalSalvo, true);
    }

    @Test
    public void deveRemoverPorId(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Long id = animalSalvo.getAnimalId();
        animalService.removeById(id);
        Assertions.assertFalse(animalRepo.findById(id).isPresent());
        rollback(animalSalvo, true);
    }

    @Test
    public void deveRemoverComFeedback(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Long id = animalSalvo.getAnimalId();
        animalService.removeByIdWithFeedback(id);
        Assertions.assertNull(animalRepo.findByAnimalId(id));
        rollback(animalSalvo, true);
    }

    @Test
    public void deveBuscarPorDonoId(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Long id = animalSalvo.getAnimalId();
        Animal animalEncontrado = animalService.findById(id);
        Assertions.assertNotNull(animalEncontrado);
        Assertions.assertEquals(animalSalvo.getOwner().getOwnerId(), animalEncontrado.getOwner().getOwnerId());
        rollback(animalSalvo, false);
    }

    @Test
    public void deveBuscarComFiltro(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Animal filtro = Animal.builder()
                .animalId(animalSalvo.getAnimalId())
                .name(animalSalvo.getName())
                .build();
        List<Animal> animaisEncontradosList = animalService.find(filtro);
        Assertions.assertNotNull(animaisEncontradosList);
        Assertions.assertFalse(animaisEncontradosList.isEmpty());
        rollback(animalSalvo, false);
    }

    @Test
    public void deveBuscarTodos(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        List<Animal> animaisList = animalService.findAll();
        Assertions.assertNotNull(animaisList);
        Assertions.assertFalse(animaisList.isEmpty());
        rollback(animalSalvo, false);
    }

    @Test
    public void deveBuscarDono(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Owner donoEncontrado = animalService.findOwnerByAnimalId(animalSalvo.getAnimalId());
        Assertions.assertNotNull(donoEncontrado);
        Assertions.assertEquals(donoEncontrado.getOwnerId(), animalSalvo.getOwner().getOwnerId());
        rollback(animalSalvo, false);
    }

    @Test
    public void deveAtualizarDono(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Owner donoAntigo = animalSalvo.getOwner();
        Owner donoNovo = ownerRepo.save(generateDono());
        Animal animalAtualizado = animalService.updateOwner(animalSalvo, donoNovo);
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalAtualizado.getOwner().getOwnerId(), donoNovo.getOwnerId());
        rollback(animalAtualizado, false);
        ownerRepo.delete(donoAntigo);
    }
}
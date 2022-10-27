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
                .nome("nome")
                .tipoAnimal(tipoAnimalRepo.save(generateTipoAnimal()))
                .dono(donoRepo.save(generateDono()))
                .build();
    }

    protected static Animal generateAnimal(TipoAnimalRepo tipoAnimalRepo, DonoRepo donoRepo){
        return Animal.builder()
                .nome("nome")
                .tipoAnimal(tipoAnimalRepo.save(generateTipoAnimal()))
                .dono(donoRepo.save(generateDono()))
                .build();
    }

    private void rollback(Animal animal){
        animalRepo.delete(animal);
        tipoAnimalRepo.delete(animal.getTipoAnimal());
        donoRepo.delete(animal.getDono());
    }

    protected static void rollbackAnimal(Animal animal, AnimalRepo animalRepo,
                                         TipoAnimalRepo tipoAnimalRepo, DonoRepo donoRepo){
        animalRepo.delete(animal);
        tipoAnimalRepo.delete(animal.getTipoAnimal());
        donoRepo.delete(animal.getDono());
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
        Dono donoAntigo = animalSalvo.getDono();
        animalSalvo.setDono(donoRepo.save(generateDono()));
        Animal animalAtualizado = animalRepo.save(animalSalvo);
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalSalvo.getAnimalId(), animalAtualizado.getAnimalId());
        rollback(animalAtualizado);
        rollbackDono(donoAntigo, donoRepo);
    }

    @Test
    public void deveAtualizar(){
        Animal animalSalvo = animalRepo.save(generateAnimal());
        animalSalvo.setNome("Nome alterado");
        Animal animalAtualizado = animalRepo.save(animalSalvo);
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalAtualizado.getAnimalId(), animalSalvo.getAnimalId());
        Assertions.assertEquals(animalAtualizado.getNome(), "Nome alterado");
        rollback(animalAtualizado);
    }

    @Test
    public void deveAtualizarTipoAnimal(){
        Animal animalSalvo = animalRepo.save(generateAnimal());
        TipoAnimal tipoAnimalAntigo = animalSalvo.getTipoAnimal();
        animalSalvo.setTipoAnimal(tipoAnimalRepo.save(generateTipoAnimal()));
        Animal animalAtualizado = animalRepo.save(animalSalvo);
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalAtualizado.getAnimalId(), animalSalvo.getAnimalId());
        Assertions.assertEquals(animalAtualizado.getTipoAnimal().getTipoAnimalId(), animalSalvo.getTipoAnimal().getTipoAnimalId());
        rollback(animalAtualizado);
        rollbackTipoAnimal(tipoAnimalAntigo, tipoAnimalRepo);
    }

    @Test
    public void deveRemover(){
        Animal animalSalvo = animalRepo.save(generateAnimal());
        Long id = animalSalvo.getAnimalId();
        animalRepo.delete(animalSalvo);
        Assertions.assertFalse(animalRepo.findById(id).isPresent());
        rollbackTipoAnimal(animalSalvo.getTipoAnimal(), tipoAnimalRepo);
        rollbackDono(animalSalvo.getDono(), donoRepo);

    }

    @Test
    public void deveBuscar(){
        Animal animalSalvo = animalRepo.save(generateAnimal());
        Long id = animalSalvo.getAnimalId();
        Assertions.assertTrue(animalRepo.findById(id).isPresent());
        rollback(animalSalvo);
    }
}
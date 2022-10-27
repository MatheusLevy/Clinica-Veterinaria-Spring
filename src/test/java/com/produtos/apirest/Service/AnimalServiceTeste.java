package com.produtos.apirest.Service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.DonoRepo;
import com.produtos.apirest.repository.TipoAnimalRepo;
import com.produtos.apirest.service.AnimalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.produtos.apirest.Service.DonoServiceTeste.generateDono;
import static com.produtos.apirest.Service.TipoAnimalServiceTeste.generateTipoAnimal;

@SpringBootTest
public class AnimalServiceTeste {

    @Autowired
    public AnimalService animalService;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public DonoRepo donoRepo;

    @Autowired
    public TipoAnimalRepo tipoAnimalRepo;


    protected static Animal generateAnimal(TipoAnimalRepo tipoAnimalRepo, DonoRepo donoRepo,
                                           Boolean initializeFields){
        Animal animal = Animal.builder()
                .nome("teste")
                .tipoAnimal(generateTipoAnimal())
                .dono(generateDono())
                .build();
        if (initializeFields){
            animal.setTipoAnimal(tipoAnimalRepo.save(animal.getTipoAnimal()));
            animal.setDono(donoRepo.save(animal.getDono()));
        }
        return animal;
    }

    private Animal generateAnimal(Boolean initializeFields){
        Animal animal = Animal.builder()
                .nome("teste")
                .tipoAnimal(generateTipoAnimal())
                .dono(generateDono())
                .build();
        if (initializeFields){
            animal.setTipoAnimal(tipoAnimalRepo.save(animal.getTipoAnimal()));
            animal.setDono(donoRepo.save(animal.getDono()));
        }
        return animal;
    }

    protected static void rollbackAnimal(Animal animal, AnimalRepo animalRepo,
                                         DonoRepo donoRepo,
                                         TipoAnimalRepo tipoAnimalRepo){
        animalRepo.delete(animal);
        donoRepo.delete(animal.getDono());
        tipoAnimalRepo.delete(animal.getTipoAnimal());
    }

    private void rollback(Animal animal, Boolean skipAnimal){
        if (!skipAnimal)
            animalRepo.delete(animal);
        donoRepo.delete(animal.getDono());
        tipoAnimalRepo.delete(animal.getTipoAnimal());
    }

    @Test
    public void deveSalvar(){
        Animal animalSalvo = animalService.salvar(generateAnimal(true));
        Assertions.assertNotNull(animalSalvo);
        rollback(animalSalvo, false);
    }

    @Test
    public void deveAtualizar(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        animalSalvo.setNome("Animal Atualizado");
        Animal animalAtualizado = animalService.atualizar(animalSalvo);
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalSalvo.getAnimalId(), animalAtualizado.getAnimalId());
        Assertions.assertEquals(animalAtualizado.getNome(), "Animal Atualizado");
        rollback(animalSalvo, false);
    }

    @Test
    public void deveRemover(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Long id = animalSalvo.getAnimalId();
        animalService.remover(animalSalvo);
        Assertions.assertFalse(animalRepo.findById(id).isPresent());
        rollback(animalSalvo, true);
    }

    @Test
    public void deveRemoverPorId(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Long id = animalSalvo.getAnimalId();
        animalService.removerPorId(id);
        Assertions.assertFalse(animalRepo.findById(id).isPresent());
        rollback(animalSalvo, true);
    }

    @Test
    public void deveRemoverComFeedback(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Long id = animalSalvo.getAnimalId();
        animalService.removerComFeedback(id);
        Assertions.assertFalse(animalRepo.findById(id).isPresent());
        rollback(animalSalvo, true);
    }

    @Test
    public void deveBuscarPorDonoId(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Long id = animalSalvo.getAnimalId();
        Animal animalEncontrado = animalService.buscarPorId(id);
        Assertions.assertNotNull(animalEncontrado);
        Assertions.assertEquals(animalSalvo.getDono().getDonoId(), animalEncontrado.getDono().getDonoId());
        rollback(animalSalvo, false);
    }

    @Test
    public void deveBuscarComFiltro(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Animal filtro = Animal.builder()
                .animalId(animalSalvo.getAnimalId())
                .nome(animalSalvo.getNome())
                .build();
        List<Animal> animaisEncontradosList = animalService.buscar(filtro);
        Assertions.assertNotNull(animaisEncontradosList);
        Assertions.assertFalse(animaisEncontradosList.isEmpty());
        rollback(animalSalvo, false);
    }

    @Test
    public void deveBuscarTodos(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        List<Animal> animaisList = animalService.buscarTodos();
        Assertions.assertNotNull(animaisList);
        Assertions.assertFalse(animaisList.isEmpty());
        rollback(animalSalvo, false);
    }

    @Test
    public void deveBuscarDono(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Dono donoEncontrado = animalService.buscarDonoPorId(animalSalvo.getAnimalId());
        Assertions.assertNotNull(donoEncontrado);
        Assertions.assertEquals(donoEncontrado.getDonoId(), animalSalvo.getDono().getDonoId());
        rollback(animalSalvo, false);
    }

    @Test
    public void deveAtualizarDono(){
        Animal animalSalvo = animalRepo.save(generateAnimal(true));
        Dono donoAntigo = animalSalvo.getDono();
        Dono donoNovo = donoRepo.save(generateDono());
        Animal animalAtualizado = animalService.atualizarDono(animalSalvo, donoNovo);
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalAtualizado.getDono().getDonoId(), donoNovo.getDonoId());
        rollback(animalAtualizado, false);
        donoRepo.delete(donoAntigo);
    }
}
package com.produtos.apirest.Service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.AnimalTypeRepo;
import com.produtos.apirest.repository.OwnerRepo;
import com.produtos.apirest.service.DonoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.produtos.apirest.Service.AnimalServiceTeste.generateAnimal;
import static com.produtos.apirest.Service.AnimalServiceTeste.rollbackAnimal;
import static com.produtos.apirest.Util.Util.generateCPF;
import static com.produtos.apirest.Util.Util.generateTelefone;

@SpringBootTest
public class DonoServiceTeste {

    @Autowired
    public DonoService donoService;

    @Autowired
    public OwnerRepo ownerRepo;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public AnimalTypeRepo animalTypeRepo;

    protected static Owner generateDono(){
        return Owner.builder()
                .name("test")
                .phone(generateTelefone())
                .cpf(generateCPF())
                .build();
    }

    private void rollback(Owner dono){
        ownerRepo.delete(dono);
    }

    protected static void rollbackDono(Owner dono, OwnerRepo ownerRepo){
        ownerRepo.delete(dono);
    }

    @Test
    public void deveSalvar(){
        Owner donoSalvo = donoService.salvar(generateDono());
        Assertions.assertNotNull(donoSalvo);
        rollback(donoSalvo);
    }

    @Test
    public void deveAtualizar(){
        Owner donoSalvo = ownerRepo.save(generateDono());
        donoSalvo.setName("Dono Atualizado");
        Owner donoAtualizado = donoService.atualizar(donoSalvo);
        Assertions.assertNotNull(donoAtualizado);
        Assertions.assertEquals(donoSalvo.getOwnerId(), donoAtualizado.getOwnerId());
        rollback(donoAtualizado);
    }

    @Test
    public void deveRemover(){
        Owner donoSalvo = ownerRepo.save(generateDono());
        Long id = donoSalvo.getOwnerId();
        donoService.remover(donoSalvo);
        Assertions.assertFalse(ownerRepo.findById(id).isPresent());
    }

    @Test
    public void deveRemoverComFeedback(){
        Owner donoSalvo = ownerRepo.save(generateDono());
        Owner donoFeedback = donoService.removerComFeedback(donoSalvo.getOwnerId());
        Assertions.assertNotNull(donoFeedback);
        Assertions.assertEquals(donoFeedback.getOwnerId(), donoSalvo.getOwnerId());
    }

    @Test
    public void deveRemoverPorId(){
        Owner donoSalvo = ownerRepo.save(generateDono());
        Long id = donoSalvo.getOwnerId();
        donoService.removerPorId(donoSalvo.getOwnerId());
        Assertions.assertFalse(ownerRepo.findById(id).isPresent());
    }
    @Test
    public void deveBuscarPorId(){
        Owner donoSalvo = ownerRepo.save(generateDono());
        Owner donoEncontrado = donoService.buscarPorId(donoSalvo.getOwnerId());
        Assertions.assertNotNull(donoEncontrado);
        Assertions.assertEquals(donoSalvo.getOwnerId(), donoEncontrado.getOwnerId());
        rollback(donoEncontrado);
    }

    @Test
    public void deveBuscarTodos(){
        Owner donoSalvo = donoService.salvar(generateDono());
        List<Owner> donosList = donoService.buscarTodos();
        Assertions.assertNotNull(donosList);
        Assertions.assertFalse(donosList.isEmpty());
        rollback(donoSalvo);
    }

    @Test
    public void deveBuscarComFiltro(){
        Owner donoSalvo = ownerRepo.save(generateDono());
        List<Owner> donosEncontradosList = donoService.buscar(donoSalvo);
        Assertions.assertNotNull(donosEncontradosList);
        Assertions.assertFalse(donosEncontradosList.isEmpty());
        rollback(donoSalvo);
    }

    @Test
    public void deveBuscarTodosAnimais(){
        Animal animal = animalRepo.save(generateAnimal(animalTypeRepo, ownerRepo, true));
        Owner donoDoAnimal = animal.getOwner();
        List<Animal> animaisList = donoService.buscarTodosAnimais(donoDoAnimal.getOwnerId());
        Assertions.assertNotNull(animaisList);
        Assertions.assertFalse(animaisList.isEmpty());
        rollbackAnimal(animal, animalRepo, ownerRepo, animalTypeRepo);
    }
}
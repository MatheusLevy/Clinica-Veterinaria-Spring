package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.DonoRepo;
import com.produtos.apirest.repository.TipoAnimalRepo;
import com.produtos.apirest.service.DonoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.produtos.apirest.Serviços.AnimalServiceTeste.generateAnimal;
import static com.produtos.apirest.Serviços.AnimalServiceTeste.rollbackAnimal;
import static com.produtos.apirest.Util.Util.generateCPF;
import static com.produtos.apirest.Util.Util.generateTelefone;

@SpringBootTest
public class DonoServiceTeste {

    @Autowired
    public DonoService donoService;

    @Autowired
    public DonoRepo donoRepo;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public TipoAnimalRepo tipoAnimalRepo;

    protected static Dono generateDono(){
        return Dono.builder()
                .nome("teste")
                .telefone(generateTelefone())
                .cpf(generateCPF())
                .build();
    }

    private void rollback(Dono dono){
        donoRepo.delete(dono);
    }

    protected static void rollbackDono(Dono dono, DonoRepo donoRepo){
        donoRepo.delete(dono);
    }

    @Test
    public void deveSalvar(){
        Dono donoSalvo = donoService.salvar(generateDono());
        Assertions.assertNotNull(donoSalvo);
        Assertions.assertNotNull(donoSalvo.getDonoId());
        rollback(donoSalvo);
    }

    @Test
    public void deveAtualizar(){
        Dono donoSalvo = donoRepo.save(generateDono());
        donoSalvo.setNome("Dono Atualizado");
        Dono donoAtualizado = donoService.atualizar(donoSalvo);
        Assertions.assertNotNull(donoAtualizado);
        Assertions.assertEquals(donoSalvo.getDonoId(), donoAtualizado.getDonoId());
        rollback(donoAtualizado);
    }

    @Test
    public void deveRemover(){
        Dono donoSalvo = donoRepo.save(generateDono());
        Long id = donoSalvo.getDonoId();
        donoService.remover(donoSalvo);
        Assertions.assertFalse(donoRepo.findById(id).isPresent());
    }

    @Test
    public void deveRemoverComFeedback(){
        Dono donoSalvo = donoRepo.save(generateDono());
        Dono donoFeedback = donoService.removerComFeedback(donoSalvo.getDonoId());
        Assertions.assertNotNull(donoFeedback);
        Assertions.assertEquals(donoFeedback.getDonoId(), donoSalvo.getDonoId());
    }

    @Test
    public void deveRemoverPorId(){
        Dono donoSalvo = donoRepo.save(generateDono());
        Long id = donoSalvo.getDonoId();
        donoService.removerPorId(donoSalvo.getDonoId());
        Assertions.assertFalse(donoRepo.findById(id).isPresent());
    }
    @Test
    public void deveBuscarPorId(){
        Dono donoSalvo = donoRepo.save(generateDono());
        Dono donoEncontrado = donoService.buscarPorId(donoSalvo.getDonoId());
        Assertions.assertNotNull(donoEncontrado);
        Assertions.assertEquals(donoSalvo.getDonoId(), donoEncontrado.getDonoId());
        rollback(donoEncontrado);
    }

    @Test
    public void deveBuscarTodos(){
        Dono donoSalvo = donoService.salvar(generateDono());
        List<Dono> donosList = donoService.buscarTodos();
        Assertions.assertNotNull(donosList);
        Assertions.assertFalse(donosList.isEmpty());
        rollback(donoSalvo);
    }

    @Test
    public void deveBuscarComFiltro(){
        Dono donoSalvo = donoRepo.save(generateDono());
        List<Dono> donosEncontradosList = donoService.buscar(donoSalvo);
        Assertions.assertNotNull(donosEncontradosList);
        Assertions.assertFalse(donosEncontradosList.isEmpty());
        rollback(donoSalvo);
    }

    @Test
    public void deveBuscarTodosAnimais(){
        Animal animal = animalRepo.save(generateAnimal(tipoAnimalRepo, donoRepo, true));
        Dono donoDoAnimal = animal.getDono();
        List<Animal> animaisList = donoService.buscarTodosAnimais(donoDoAnimal.getDonoId());
        Assertions.assertNotNull(animaisList);
        Assertions.assertFalse(animaisList.isEmpty());
        rollbackAnimal(animal, animalRepo, donoRepo, tipoAnimalRepo);
    }
}

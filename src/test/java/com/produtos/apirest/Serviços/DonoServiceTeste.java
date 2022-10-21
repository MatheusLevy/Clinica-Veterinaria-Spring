package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.DonoRepo;
import com.produtos.apirest.repository.TipoAnimalRepo;
import com.produtos.apirest.service.DonoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    Random random = new Random();
    @Test
    public void deveSalvar(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Test")
                .build();

        //Ação
        Dono donoRetorno = donoService.salvar(dono);

        //Verificação
        Assertions.assertNotNull(donoRetorno);
        Assertions.assertNotNull(donoRetorno.getDonoId());

        //Rollback
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizar(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Test")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        //Ação
        donoRetorno.setNome("Dono Atualizado");
        Dono donoAtualizado = donoService.atualizar(donoRetorno);

        //Verificação
        Assertions.assertNotNull(donoAtualizado);
        Assertions.assertEquals(donoRetorno.getDonoId(), donoAtualizado.getDonoId());

        //Rollback
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveRemover(){
        //Cenário
        Dono dono = Dono.builder().nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Test")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        //Ação
        donoService.remover(donoRetorno);

        //Verificação
        Optional<Dono> donoTemp = donoRepo.findById(donoRetorno.getDonoId());
        Assertions.assertNotNull(donoTemp);
        Assertions.assertFalse(donoTemp.isPresent());
    }

    @Test
    public void deveRemoverComFeedback(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Test")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        //Ação
        Dono donoRemovido = donoService.removerComFeedback(donoRetorno.getDonoId());

        //Verificação
        Assertions.assertNotNull(donoRemovido);
        Assertions.assertEquals(donoRemovido.getDonoId(), donoRetorno.getDonoId());
    }

    @Test
    public void deveRemoperPorId(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Test")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        //Ação
        donoService.removerPorId(donoRetorno.getDonoId());

        //Verificação
        Optional<Dono> donoTemp = donoRepo.findById(donoRetorno.getDonoId());
        Assertions.assertNotNull(donoTemp);
        Assertions.assertFalse(donoTemp.isPresent());
    }
    @Test
    public void deveBuscarDonoPorId(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Test")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        //Ação
        Dono donoBuscado = donoService.buscarPorId(donoRetorno.getDonoId());

        //Verificação
        Assertions.assertNotNull(donoBuscado);
        Assertions.assertEquals(donoRetorno.getDonoId(), donoBuscado.getDonoId());

        //Rollback
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveBuscarTodos(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Test")
                .build();
        Dono donoRetorno = donoService.salvar(dono);

        //Ação
        List<Dono> donos = donoService.buscarTodos();

        //Verificação
        Assertions.assertNotNull(donos);
        Assertions.assertFalse(donos.isEmpty());

        //Rollback
        donoRepo.delete(donoRetorno);
    }
    @Test
    public void deveBuscarComFiltro(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Test")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        //Ação
        List<Dono> donoBuscado = donoService.buscar(donoRetorno);

        //Verificação
        Assertions.assertNotNull(donoBuscado);
        Assertions.assertFalse(donoBuscado.isEmpty());

        //Rollback
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveBuscarTodosAnimais(){
        //Cenário
        Dono dono = Dono.builder().nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Test")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipo = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);

        Animal animal1 = Animal.builder()
                .nome("Animal 1 Teste")
                .tipoAnimal(tipoRetorno)
                .dono(donoRetorno)
                .build();
        Animal animal1Retorno = animalRepo.save(animal1);

        Animal animal2 = Animal.builder()
                .nome("Animal 2 Teste")
                .tipoAnimal(tipoRetorno)
                .dono(donoRetorno)
                .build();
        Animal animal2Retorno = animalRepo.save(animal2);

        //Ação
        List<Animal> animais = donoService.buscarTodosAnimais(donoRetorno.getDonoId());

        //Verificação
        Assertions.assertNotNull(animais);
        Assertions.assertFalse(animais.isEmpty());

        //Ação
        animalRepo.delete(animal1Retorno);
        animalRepo.delete(animal2Retorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }
}

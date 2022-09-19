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

@SpringBootTest
public class DonoServiceTeste {

    @Autowired
    public DonoService donoService;

    @Autowired
    public DonoRepo donoRepo;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public TipoAnimalRepo tipo_animalRepo;

    @Test
    public void deveSalvar(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF test").telefone("Telefone Test").build();
        Dono donoRetorno = donoService.salvar(dono);

        Assertions.assertNotNull(donoRetorno);
        Assertions.assertNotNull(donoRetorno.getDonoId());

        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizar(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF test").telefone("Telefone Test").build();
        Dono donoRetorno = donoRepo.save(dono);

        donoRetorno.setNome("Dono Atualizado");
        Dono donoAtualizado = donoService.atualizar(donoRetorno);

        Assertions.assertNotNull(donoAtualizado);
        Assertions.assertEquals(donoRetorno.getDonoId(), donoAtualizado.getDonoId());

        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveRemover(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF test").telefone("Telefone Test").build();
        Dono donoRetorno = donoRepo.save(dono);

        donoService.remover(donoRetorno);

        Optional<Dono> donoTemp = donoRepo.findById(donoRetorno.getDonoId());
        Assertions.assertNotNull(donoTemp);
        Assertions.assertFalse(donoTemp.isPresent());
    }

    @Test
    public void deveRemoverComFeedback(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF test").telefone("Telefone Test").build();
        Dono donoRetorno = donoRepo.save(dono);

        Dono donoRemovido = donoService.removerFeedback(donoRetorno);

        Assertions.assertNotNull(donoRemovido);
        Assertions.assertEquals(donoRemovido.getDonoId(), donoRetorno.getDonoId());
    }

    @Test
    public void deveBuscarDonoPorId(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF test").telefone("Telefone Test").build();
        Dono donoRetorno = donoRepo.save(dono);

        Dono donoBuscado = donoService.buscarDonoPorId(donoRetorno);

        Assertions.assertNotNull(donoBuscado);
        Assertions.assertEquals(donoRetorno.getDonoId(), donoBuscado.getDonoId());

        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveBuscarComFiltro(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF test").telefone("Telefone Test").build();
        Dono donoRetorno = donoRepo.save(dono);

        List<Dono> donoBuscado = donoService.buscar(donoRetorno);

        Assertions.assertNotNull(donoBuscado);
        Assertions.assertFalse(donoBuscado.isEmpty());

        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveBuscarTodosAnimais(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF test").telefone("Telefone Test").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipoRetorno = tipo_animalRepo.save(tipo);
        Animal animal1 = Animal.builder().nome("Animal 1 Teste").tipoAnimal(tipoRetorno).dono(donoRetorno).build();
        Animal animal1Retorno = animalRepo.save(animal1);
        Animal animal2 = Animal.builder().nome("Animal 2 Teste").tipoAnimal(tipoRetorno).dono(donoRetorno).build();
        Animal animal2Retorno = animalRepo.save(animal2);

        List<Animal> animais = donoService.buscarTodosAnimais(donoRetorno);

        Assertions.assertNotNull(animais);
        Assertions.assertFalse(animais.isEmpty());

        animalRepo.delete(animal1Retorno);
        animalRepo.delete(animal2Retorno);
        tipo_animalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }
}

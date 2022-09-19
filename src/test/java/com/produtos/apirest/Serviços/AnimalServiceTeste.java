package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.DonoRepo;
import com.produtos.apirest.repository.TipoAnimalRepo;
import com.produtos.apirest.service.AnimalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void deveSalvar(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo = TipoAnimal.builder().nome("Tipo Teste").build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);
        Animal animal = Animal.builder().nome("Animal Teste").tipoAnimal(tipoRetorno).dono(donoRetorno).build();
        Animal animalRetorno = animalService.salvar(animal);
        Assertions.assertNotNull(animalRetorno);
        Assertions.assertNotNull(animalRetorno.getAnimalId());
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizar(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo = TipoAnimal.builder().nome("Tipo Teste").build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);
        Animal animal = Animal.builder().nome("Animal Teste").tipoAnimal(tipoRetorno).dono(donoRetorno).build();
        Animal animalRetorno = animalRepo.save(animal);

        animalRetorno.setNome("Animal Atualizado");
        Animal animalAtualizado = animalService.atualizar(animalRetorno);
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalRetorno.getAnimalId(), animalAtualizado.getAnimalId());
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveRemover(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo = TipoAnimal.builder().nome("Tipo Teste").build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);
        Animal animal = Animal.builder().nome("Animal Teste").tipoAnimal(tipoRetorno).dono(donoRetorno).build();
        Animal animalRetorno = animalRepo.save(animal);

        animalService.remover(animalRetorno);

        Optional<Animal> animalTemp = animalRepo.findById(animalRetorno.getAnimalId());
        Assertions.assertNotNull(animalTemp);
        Assertions.assertFalse(animalTemp.isPresent());

        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveRemoverComFeedBack(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo = TipoAnimal.builder().nome("Tipo Teste").build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);
        Animal animal = Animal.builder().nome("Animal Teste").tipoAnimal(tipoRetorno).dono(donoRetorno).build();
        Animal animalRetorno = animalRepo.save(animal);

        Animal animalFeedback = animalService.removerFeedback(animalRetorno);

        Assertions.assertNotNull(animalFeedback);
        Assertions.assertNotNull(animalFeedback.getAnimalId());

        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveBuscarPorDonoId(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo = TipoAnimal.builder().nome("Tipo Teste").build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);
        Animal animal = Animal.builder().nome("Animal Teste").tipoAnimal(tipoRetorno).dono(donoRetorno).build();
        Animal animalRetorno = animalRepo.save(animal);

        Animal animalBuscado = animalService.buscarDonoPorId(animalRetorno);

        Assertions.assertNotNull(animalBuscado);
        Assertions.assertNotNull(animalBuscado.getAnimalId());
        Assertions.assertEquals(animalRetorno.getDono().getDonoId(), animalBuscado.getDono().getDonoId());

        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveBuscarComFiltro(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo = TipoAnimal.builder().nome("Tipo Teste").build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);
        Animal animal = Animal.builder().nome("Animal Teste").tipoAnimal(tipoRetorno).dono(donoRetorno).build();
        Animal animalRetorno = animalRepo.save(animal);

        List<Animal> animais = animalService.buscar(animalRetorno);

        Assertions.assertNotNull(animais);
        Assertions.assertFalse(animais.isEmpty());

        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveBuscarDono(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo = TipoAnimal.builder().nome("Tipo Teste").build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);
        Animal animal = Animal.builder().nome("Animal Teste").tipoAnimal(tipoRetorno).dono(donoRetorno).build();
        Animal animalRetorno = animalRepo.save(animal);

        Dono donoBuscado = animalService.buscarDono(animalRetorno);

        Assertions.assertNotNull(donoBuscado);
        Assertions.assertNotNull(donoBuscado.getDonoId());

        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizarDono(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo = TipoAnimal.builder().nome("Tipo Teste").build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);
        Animal animal = Animal.builder().nome("Animal Teste").tipoAnimal(tipoRetorno).dono(donoRetorno).build();
        Animal animalRetorno = animalRepo.save(animal);

        Dono novoDono = Dono.builder().nome("Novo Dono").cpf("Novo CPF").telefone("Telefone").build();
        Dono novoDonoRetorno = donoRepo.save(novoDono);

        Animal animalAtualizado = animalService.atualizarDono(novoDonoRetorno, animalRetorno);

        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalAtualizado.getDono().getDonoId(), novoDonoRetorno.getDonoId());

        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
        donoRepo.delete(novoDonoRetorno);
    }
}

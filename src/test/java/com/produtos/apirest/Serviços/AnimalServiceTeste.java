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
import java.util.Random;

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

    Random random = new Random();
    @Test
    public void deveSalvar(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipo = TipoAnimal.builder()
                .nome("Tipo Teste")
                .build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);

        Animal animal = Animal.builder()
                .nome("Animal Teste")
                .tipoAnimal(tipoRetorno)
                .dono(donoRetorno).build();

        //Ação
        Animal animalRetorno = animalService.salvar(animal);

        //Verificação
        Assertions.assertNotNull(animalRetorno);
        Assertions.assertNotNull(animalRetorno.getAnimalId());

        //Rollback
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizar(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipo = TipoAnimal.builder()
                .nome("Tipo Teste")
                .build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);

        Animal animal = Animal.builder()
                .nome("Animal Teste")
                .tipoAnimal(tipoRetorno)
                .dono(donoRetorno)
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        //Ação
        animalRetorno.setNome("Animal Atualizado");
        Animal animalAtualizado = animalService.atualizar(animalRetorno);

        //Verificação
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalRetorno.getAnimalId(), animalAtualizado.getAnimalId());
        Assertions.assertEquals(animalAtualizado.getNome(), "Animal Atualizado");

        //Rollback
        animalRepo.delete(animalAtualizado);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }
    @Test
    public void deveRemover(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipo = TipoAnimal.builder()
                .nome("Tipo Teste")
                .build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);

        Animal animal = Animal.builder().nome("Animal Teste")
                .tipoAnimal(tipoRetorno)
                .dono(donoRetorno)
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        //Ação
        animalService.remover(animalRetorno);

        //Verificação
        Optional<Animal> animalTemp = animalRepo.findById(animalRetorno.getAnimalId());
        Assertions.assertNotNull(animalTemp);
        Assertions.assertFalse(animalTemp.isPresent());

        //Rollback
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveRemoverPorId(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipo = TipoAnimal.builder()
                .nome("Tipo Teste")
                .build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);

        Animal animal = Animal.builder().nome("Animal Teste")
                .tipoAnimal(tipoRetorno)
                .dono(donoRetorno)
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        //Ação
        animalService.removerPorId(animalRetorno.getAnimalId());

        //Verificação
        Optional<Animal> animalTemp = animalRepo.findById(animalRetorno.getAnimalId());
        Assertions.assertNotNull(animalTemp);
        Assertions.assertFalse(animalTemp.isPresent());

        //Rollback
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveRemoverComFeedback(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipo = TipoAnimal.builder()
                .nome("Tipo Teste")
                .build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);

        Animal animal = Animal.builder().nome("Animal Teste")
                .tipoAnimal(tipoRetorno)
                .dono(donoRetorno)
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        //Ação
        Animal feedback = animalService.removerComFeedback(animalRetorno.getAnimalId());

        //Verificação
        Optional<Animal> animalTemp = animalRepo.findById(animalRetorno.getAnimalId());
        Assertions.assertNotNull(animalTemp);
        Assertions.assertFalse(animalTemp.isPresent());
        animalTemp = animalRepo.findById(feedback.getAnimalId());
        Assertions.assertNotNull(animalTemp);
        Assertions.assertFalse(animalTemp.isPresent());
        Assertions.assertEquals(animalRetorno.getAnimalId(), feedback.getAnimalId());

        //Rollback
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveBuscarPorDonoId(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipo = TipoAnimal.builder()
                .nome("Tipo Teste")
                .build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);

        Animal animal = Animal.builder()
                .nome("Animal Teste")
                .tipoAnimal(tipoRetorno)
                .dono(donoRetorno).build();
        Animal animalRetorno = animalRepo.save(animal);

        //Ação
        Animal animalBuscado = animalService.buscarPorId(animalRetorno.getAnimalId());

        //Verificação
        Assertions.assertNotNull(animalBuscado);
        Assertions.assertNotNull(animalBuscado.getAnimalId());
        Assertions.assertEquals(animalRetorno.getDono().getDonoId(), animalBuscado.getDono().getDonoId());

        //Rollback
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveBuscarComFiltro(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipo = TipoAnimal.builder()
                .nome("Tipo Teste")
                .build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);

        Animal animal = Animal.builder()
                .nome("Animal Teste")
                .tipoAnimal(tipoRetorno)
                .dono(donoRetorno)
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        //Ação
        Animal filtro = Animal.builder()
                .animalId(animalRetorno.getAnimalId())
                .nome(animalRetorno.getNome())
                .build();
        List<Animal> animais = animalService.buscar(filtro);

        //Verificação
        Assertions.assertNotNull(animais);
        Assertions.assertFalse(animais.isEmpty());

        //Rollback
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveBuscarTodos(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipo = TipoAnimal.builder()
                .nome("Tipo Teste")
                .build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);

        Animal animal = Animal.builder()
                .nome("Animal Teste")
                .tipoAnimal(tipoRetorno)
                .dono(donoRetorno)
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        Animal animal2 = Animal.builder()
                .nome("Animal Teste 2")
                .tipoAnimal(tipoRetorno)
                .dono(donoRetorno)
                .build();
        Animal animal2Retorno = animalRepo.save(animal2);

        //Ação
        List<Animal> animais = animalService.buscarTodos();

        //Verificação
        Assertions.assertNotNull(animais);
        Assertions.assertFalse(animais.isEmpty());

        //Rollback
        //Rollback
        animalRepo.delete(animalRetorno);
        animalRepo.delete(animal2Retorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveBuscarDono(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(999999)))
                .telefone("Telefone Teste")
                .build();

        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipo = TipoAnimal.builder()
                .nome("Tipo Teste")
                .build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);

        Animal animal = Animal.builder()
                .nome("Animal Teste")
                .tipoAnimal(tipoRetorno)
                .dono(donoRetorno)
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        //Ação
        Dono donoBuscado = animalService.buscarDonoPorId(animalRetorno.getAnimalId());

        //Verificação
        Assertions.assertNotNull(donoBuscado);
        Assertions.assertNotNull(donoBuscado.getDonoId());
        Assertions.assertEquals(donoBuscado.getDonoId(), animalRetorno.getDono().getDonoId());

        //Rollback
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizarDono(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipo = TipoAnimal.builder()
                .nome("Tipo Teste")
                .build();
        TipoAnimal tipoRetorno = tipoAnimalRepo.save(tipo);

        Animal animal = Animal.builder()
                .nome("Animal Teste")
                .tipoAnimal(tipoRetorno)
                .dono(donoRetorno)
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        Dono novoDono = Dono.builder()
                .nome("Novo Dono")
                .cpf(String.valueOf(random.nextInt(999999)))
                .telefone("Telefone")
                .build();
        Dono novoDonoRetorno = donoRepo.save(novoDono);

        //Ação
        Animal animalAtualizado = animalService.atualizarDono(animalRetorno, novoDonoRetorno);

        //Verificação
        Assertions.assertNotNull(animalAtualizado);
        Assertions.assertEquals(animalAtualizado.getDono().getDonoId(), novoDonoRetorno.getDonoId());

        //Rollback
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoRetorno);
        donoRepo.delete(donoRetorno);
        donoRepo.delete(novoDonoRetorno);
    }
}

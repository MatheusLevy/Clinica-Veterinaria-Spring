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

import java.util.Optional;
import java.util.Random;

@SpringBootTest
public class AnimalTeste {
    @Autowired
    public AnimalRepo repo;

    @Autowired
    public TipoAnimalRepo tipoAnimalRepo;

    @Autowired
    public DonoRepo donoRepo;

    Random random = new Random();

    //TODO: Refatorar
    @Test
    public void deveCriarAnimal(){
        //Cenário
        TipoAnimal novoTipoAnimal = TipoAnimal.builder()
                .nome("Gato")
                .build();
        TipoAnimal retornoTipoAnimal = tipoAnimalRepo.save(novoTipoAnimal);

        Dono novoDono = Dono.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("111111")
                .build();
        Dono retornoDono = donoRepo.save(novoDono);

        Animal novo = Animal.builder()
                .nome("Pepper")
                .tipoAnimal(retornoTipoAnimal).
                dono(retornoDono)
                .build();

        //Ação
        Animal retorno = repo.save(novo);

        //Verificação
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(novo.getNome(), retorno.getNome());

        //Rollback
        repo.delete(retorno);
        tipoAnimalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);
    }

    @Test
    public void deveRemoverAnimal(){
        //Cenário
        TipoAnimal novoTipoAnimal = TipoAnimal.builder()
                .nome("Gato")
                .build();
        TipoAnimal retornoTipoAnimal = tipoAnimalRepo.save(novoTipoAnimal);

        Dono novoDono = Dono.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("111111")
                .build();
        Dono retornoDono = donoRepo.save(novoDono);

        Animal novo = Animal.builder()
                .nome("Pepper")
                .tipoAnimal(retornoTipoAnimal)
                .dono(retornoDono)
                .build();
        Animal retorno = repo.save(novo);

        //Ação
        repo.delete(retorno);

        //Verificação
        Optional<Animal> temp = repo.findById(retorno.getAnimalId());
        Assertions.assertFalse(temp.isPresent());

        //Rollback
        tipoAnimalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);

    }

    @Test
    public void deveBuscarAnimal(){
        //Cenário
        TipoAnimal novoTipoAnimal = TipoAnimal.builder().nome("Gato").build();
        TipoAnimal retornoTipoAnimal = tipoAnimalRepo.save(novoTipoAnimal);
        Dono novoDono = Dono.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("111111").build();
        Dono retornoDono = donoRepo.save(novoDono);

        Animal novo = Animal.builder()
                .nome("Pepper")
                .tipoAnimal(retornoTipoAnimal)
                .dono(retornoDono)
                .build();
        Animal retorno = repo.save(novo);

        //Ação
        Optional<Animal> temp = repo.findById(retorno.getAnimalId());

        //Verificação
        Assertions.assertTrue(temp.isPresent());

        //Rollback
        repo.delete(retorno);
        donoRepo.delete(retornoDono);
        tipoAnimalRepo.delete(retornoTipoAnimal);
    }

    @Test
    public void deveAlterarDonodoAnimal(){
        //Cenário
        TipoAnimal novoTipoAnimal = TipoAnimal.builder()
                .nome("Gato")
                .build();
        TipoAnimal retornoTipoAnimal = tipoAnimalRepo.save(novoTipoAnimal);

        Dono novoDono = Dono.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("111111")
                .build();
        Dono retornoDono = donoRepo.save(novoDono);

        Animal novo = Animal.builder()
                .nome("Pepper")
                .tipoAnimal(retornoTipoAnimal)
                .dono(retornoDono)
                .build();
        Animal retorno = repo.save(novo);

        Dono novoOutroDono = Dono.builder()
                .nome("João")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("1111111")
                .build();
        Dono retornoOutroDono = donoRepo.save(novoOutroDono);

        //Ação
        retorno.setDono(retornoOutroDono);
        Animal alteradoAnimal = repo.save(retorno);

        //Verificação
        Assertions.assertNotNull(alteradoAnimal);
        Assertions.assertEquals(retorno.getAnimalId(), alteradoAnimal.getAnimalId());

        //Rollback
        repo.delete(alteradoAnimal);
        tipoAnimalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);
        donoRepo.delete(retornoOutroDono);
    }

    @Test
    public void deveAtualizarAnimal(){
        //Cenário
        TipoAnimal novoTipoAnimal = TipoAnimal.builder()
                .nome("Gato")
                .build();
        TipoAnimal retornoTipoAnimal = tipoAnimalRepo.save(novoTipoAnimal);

        Dono novoDono = Dono.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("111111")
                .build();
        Dono retornoDono = donoRepo.save(novoDono);

        Animal novo = Animal.builder()
                .nome("Pepper")
                .tipoAnimal(retornoTipoAnimal)
                .dono(retornoDono)
                .build();
        Animal retorno = repo.save(novo);

        //Ação
        retorno.setNome("Nome alterado");
        Animal atualizado = repo.save(retorno);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(atualizado.getAnimalId(), retorno.getAnimalId());
        Assertions.assertEquals(atualizado.getNome(), "Nome alterado");

        //Rollback
        repo.delete(atualizado);
        tipoAnimalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);
    }

    @Test
    public void deveAtualizarTipoAnimal(){
        //Cenário
        TipoAnimal novoTipoAnimal = TipoAnimal.builder()
                .nome("Gato")
                .build();
        TipoAnimal retornoTipoAnimal = tipoAnimalRepo.save(novoTipoAnimal);

        TipoAnimal outroTipoAnimal = TipoAnimal.builder()
                .nome("Novo Tipo Animal")
                .build();
        TipoAnimal retornoOutroTipoAnimal = tipoAnimalRepo.save(outroTipoAnimal);

        Dono novoDono = Dono.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("111111")
                .build();
        Dono retornoDono = donoRepo.save(novoDono);

        Animal novo = Animal.builder()
                .nome("Pepper")
                .tipoAnimal(retornoTipoAnimal)
                .dono(retornoDono)
                .build();
        Animal retorno = repo.save(novo);

        //Ação
        retorno.setTipoAnimal(retornoOutroTipoAnimal);
        Animal atualizado = repo.save(retorno);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(atualizado.getAnimalId(), retorno.getAnimalId());
        Assertions.assertEquals(atualizado.getTipoAnimal().getTipoAnimalId(), retornoOutroTipoAnimal.getTipoAnimalId());

        //Rollback
        repo.delete(atualizado);
        tipoAnimalRepo.delete(retornoTipoAnimal);
        tipoAnimalRepo.delete(retornoOutroTipoAnimal);
        donoRepo.delete(retornoDono);
    }



}

package com.produtos.apirest.Model;

import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.repository.TipoAnimalRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Optional;

@SpringBootTest
public class TipoAnimalTeste {
    @Autowired
    public TipoAnimalRepo repo;

    @Test
    public void deveCriarTipoAnimal(){
        //Cenário
        TipoAnimal novo = TipoAnimal.builder().nome("Cão").build();

        //Ação
        TipoAnimal retorno = repo.save(novo);

        //Verificaão
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(novo.getNome(), retorno.getNome());

        //Rollback
        repo.delete(retorno);
    }

    @Test
    public void deveRemoverTipoAnimal(){
        //Cenário
        TipoAnimal novo = TipoAnimal.builder().nome("Cão").build();
        TipoAnimal retorno = repo.save(novo);

        //Ação
        repo.delete(retorno);

        //Verificação
        Optional<TipoAnimal> temp = repo.findById(retorno.getTipoAnimalId());
        Assertions.assertFalse(temp.isPresent());
    }

    @Test
    public void deveBuscarTipoAnimal(){
        //Cenário
        TipoAnimal novo = TipoAnimal.builder().nome("Cão").build();
        TipoAnimal retorno = repo.save(novo);

        //Ação
        Optional<TipoAnimal> temp = repo.findById(retorno.getTipoAnimalId());

        //Verificação
        Assertions.assertTrue(temp.isPresent());

        //Rollback
        repo.delete(retorno);
    }

    @Test
    public void deveAtualizar(){
        //Cenário
        TipoAnimal novo = TipoAnimal.builder().nome("Cão").build();
        TipoAnimal retorno = repo.save(novo);

        //Ação
        retorno.setNome("Novo nome");
        TipoAnimal atualizado = repo.save(retorno);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(atualizado.getTipoAnimalId(), retorno.getTipoAnimalId());
        Assertions.assertEquals(atualizado.getNome(), "Novo nome");

        //Rollback
        repo.delete(atualizado);

    }
}

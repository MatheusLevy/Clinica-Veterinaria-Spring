package com.produtos.apirest.Model;

import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.repository.TipoAnimalRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class TipoAnimalTeste {
    @Autowired
    public TipoAnimalRepo repo;

    @Test
    public void deveCriarTipo_animal(){
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
    public void deveRemoverTipo_animal(){
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
    public void deveBuscarTipo_animal(){
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
}

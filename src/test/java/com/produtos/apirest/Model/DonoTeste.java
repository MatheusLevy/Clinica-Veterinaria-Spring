package com.produtos.apirest.Model;

import com.produtos.apirest.models.Dono;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.DonoRepo;
import com.produtos.apirest.repository.TipoAnimalRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class DonoTeste {

    @Autowired
    public DonoRepo repo;
    public AnimalRepo repoAnimal;
    public TipoAnimalRepo repoTipoAnimal;

    @Test
    public void deveCriarDono(){
        //Cenário
        Dono novo = Dono.builder().nome("Marcos").cpf("123").telefone("111111").build();

        //Ação
        Dono retorno = repo.save(novo);

        //Verificação
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(novo.getNome(), retorno.getNome());

        //Rollback
        repo.delete(retorno);
    }

    @Test
    public void deveRemoverDono(){
        //Cenário
        Dono novo = Dono.builder().nome("Marcos").cpf("123").telefone("111111").build();
        Dono retorno = repo.save(novo);

        //Ação
        repo.delete(retorno);

        //Verificação
        Optional<Dono> temp = repo.findById(retorno.getDonoId());
        Assertions.assertFalse(temp.isPresent());
    }

    @Test
    public void deveBuscarDono(){
        //Cenário
        Dono novo = Dono.builder().nome("Marcos").cpf("123").telefone("111111").build();
        Dono retorno = repo.save(novo);

        //Ação
        Optional<Dono> temp = repo.findById(retorno.getDonoId());

        //Verificação
        Assertions.assertTrue(temp.isPresent());

        //Rollback
        repo.delete(retorno);
    }
}

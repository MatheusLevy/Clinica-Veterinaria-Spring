package com.produtos.apirest.Model;

import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.repository.TipoConsultaRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class TipoConsultaTeste {
    @Autowired
    public TipoConsultaRepo repo;

    @Test
    public void deveCriarTipoConsulta(){
        //Cenário
        TipoConsulta novo = TipoConsulta.builder().nome("Retorno").build();

        //Ação
        TipoConsulta retorno = repo.save(novo);

        //Verificação
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(novo.getNome(), retorno.getNome());

        //Rollback
        repo.delete(retorno);
    }

    @Test
    public void deveRemoverTipoConsulta(){
        //Cenário
        TipoConsulta novo = TipoConsulta.builder().nome("Retorno").build();
        TipoConsulta retorno = repo.save(novo);

        //Ação
        repo.delete(retorno);

        //Verificação
        Optional<TipoConsulta> temp = repo.findById(retorno.getTipoConsultaId());
        Assertions.assertFalse(temp.isPresent());
    }

    @Test
    public void deveBuscarTipoConsulta(){
        //Cenário
        TipoConsulta novo = TipoConsulta.builder().nome("Retorno").build();
        TipoConsulta retorno = repo.save(novo);

        //Ação
        Optional<TipoConsulta> temp = repo.findById(retorno.getTipoConsultaId());

        //Verificação
        Assertions.assertTrue(temp.isPresent());

        //Rollback
        repo.delete(retorno);
    }

}

package com.produtos.apirest.Model;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.repository.AreaRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class AreaTeste {
    @Autowired
    public AreaRepo repo;

    @Test
    public void deveCriarArea(){
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();

        //Ação
        Area retornoArea = repo.save(novaArea);

        //Testes
        Assertions.assertNotNull(retornoArea);
        Assertions.assertEquals(novaArea.getNome(), retornoArea.getNome());

        //Rollback
        repo.delete(retornoArea);
    }
    @Test
    public void deveRemoverArea(){
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();
        Area retornoArea = repo.save(novaArea);

        //Ação
        Long id = retornoArea.getAreaId();
        repo.deleteById(id);

        //Verificação
        Optional<Area> temp = repo.findById(id);
        Assertions.assertFalse(temp.isPresent());
    }

    @Test
    public void deveBuscarArea(){
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();
        Area retornoArea = repo.save(novaArea);

        //Ação
        Optional<Area> temp = repo.findById(retornoArea.getAreaId());

        //Verificação
        Assertions.assertTrue(temp.isPresent());

        //Rollback
        repo.delete(retornoArea);
    }

    @Test
    public void deveAtualizarArea(){
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();
        Area retornoArea = repo.save(novaArea);

        //Ação
        retornoArea.setNome("Nome Novo");
        Area atualizado = repo.save(retornoArea);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(atualizado.getAreaId(), retornoArea.getAreaId());
        Assertions.assertEquals(atualizado.getNome(), "Nome Novo");

        //Rollback
        repo.delete(retornoArea);
    }
}

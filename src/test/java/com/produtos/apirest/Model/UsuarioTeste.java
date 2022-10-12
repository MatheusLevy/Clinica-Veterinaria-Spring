package com.produtos.apirest.Model;

import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.repository.UsuarioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Optional;

@SpringBootTest
public class UsuarioTeste {

    @Autowired
    public UsuarioRepo repo;

    @Test
    public void deveCriarUsuario(){
        //Cenário
        Usuario novoUsuario = Usuario.builder()
                .username("teste")
                .senha("senha")
                .nivel("AS")
                .build();
        //Ação
        Usuario retornoUsuario = repo.save(novoUsuario);

        //Verificação
        Assertions.assertNotNull(retornoUsuario);

        //Rollback
        repo.delete(retornoUsuario);
    }

    @Test
    public void deveRemoverUsuario(){
        //Cenário
        Usuario novoUsuario = Usuario.builder()
                .username("teste")
                .senha("senha")
                .nivel("AS")
                .build();
        Usuario retornoUsuario = repo.save(novoUsuario);

        //Ação
        repo.delete(retornoUsuario);

        //Verificação
        Optional<Usuario> temp = repo.findById(retornoUsuario.getUsuarioId());
        Assertions.assertNotNull(temp);
        Assertions.assertFalse(temp.isPresent());
    }

    @Test
    public void deveBuscarUsuario(){
        //Cenário
        Usuario novoUsuario = Usuario.builder()
                .username("teste")
                .senha("senha")
                .nivel("AS")
                .build();
        Usuario retornoUsuario = repo.save(novoUsuario);

        //Ação
        Optional<Usuario> temp = repo.findById(retornoUsuario.getUsuarioId());

        //Verificação
        Assertions.assertNotNull(temp);
        Assertions.assertTrue(temp.isPresent());

        //Rollback
        repo.delete(retornoUsuario);
    }

    @Test
    public void deveBuscarUsuarioPeloUsername(){
        //Cenário
        Usuario novoUsuario = Usuario.builder()
                .username("teste")
                .senha("senha")
                .nivel("AS")
                .build();
        Usuario retornoUsuario = repo.save(novoUsuario);

        //Ação
        Usuario usarioFind = repo.findByUsername(retornoUsuario.getUsername());

        //Verificação
        Assertions.assertNotNull(usarioFind);
        Assertions.assertEquals(usarioFind.getUsuarioId(), retornoUsuario.getUsuarioId());

        //Rollback
        repo.delete(retornoUsuario);
    }

    @Test
    public void deveAtualizarUsuario(){
        //Cenário
        Usuario novoUsuario = Usuario.builder()
                .username("teste")
                .senha("senha")
                .nivel("AS")
                .build();
        Usuario retornoUsuario = repo.save(novoUsuario);

        //Ação
        retornoUsuario.setUsername("Novo Username");
        Usuario atualizado = repo.save(retornoUsuario);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(atualizado.getUsuarioId(), retornoUsuario.getUsuarioId());
        Assertions.assertEquals(atualizado.getUsername(), "Novo Username");

        //Rollback
        repo.delete(atualizado);
    }

}

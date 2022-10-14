package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.repository.UsuarioRepo;
import com.produtos.apirest.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.swing.text.html.Option;
import java.util.Optional;

@SpringBootTest
public class UsarioServiceTeste {

    @Autowired
    public UsuarioRepo usuarioRepo;

    @Autowired
    public UsuarioService usuarioService;


    @Test
    public void deveSalvar(){
        //Cenário
        Usuario usuario = Usuario.builder()
                .username("User")
                .senha("senha")
                .nivel("SA")
                .build();
        //Ação
        Usuario usuarioRetorno = usuarioRepo.save(usuario);

        //Verificação
        Assertions.assertNotNull(usuarioRetorno);
        Assertions.assertNotNull(usuarioRetorno.getUsuarioId());

        //Rollback
        usuarioRepo.delete(usuarioRetorno);
    }

    @Test
    public void deveAutenticar(){
        //Cenário
        Usuario usuario = Usuario.builder()
                .username("User")
                .senha("senha")
                .nivel("SA")
                .build();
        Usuario usuarioRetorno = usuarioService.salvar(usuario);

        //Ação
        Usuario autenticado = usuarioService.autenticar(usuario.getUsername(), "senha");

        //Verificação
        Assertions.assertNotNull(autenticado.getUsuarioId());

        //Rollback
        usuarioRepo.delete(usuarioRetorno);
    }

    @Test
    public void deveBuscarPorUsername(){
        //Cenário
        Usuario usuario = Usuario.builder()
                .username("User")
                .senha("senha")
                .nivel("SA")
                .build();
        Usuario usuarioRetorno = usuarioService.salvar(usuario);

        //Cenário
        Usuario buscado = usuarioService.buscarPorUsername(usuarioRetorno.getUsername());

        //Verificação
        Assertions.assertNotNull(buscado);
        Assertions.assertEquals(buscado.getUsuarioId(), usuarioRetorno.getUsuarioId());

        //Rollback
        usuarioRepo.delete(usuarioRetorno);
    }

    @Test
    public void deveAtualizar(){
        //Cenário
        Usuario usuario = Usuario.builder()
                .username("User")
                .senha("senha")
                .nivel("SA")
                .build();
        Usuario usuarioRetorno = usuarioService.salvar(usuario);

        //Ação
        usuarioRetorno.setUsername("Novo Username");
        Usuario atualizado = usuarioService.atualizar(usuarioRetorno);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(atualizado.getUsuarioId(), usuarioRetorno.getUsuarioId());

        //Rollback
        usuarioRepo.delete(atualizado);
    }

    @Test
    public void deveRemover(){
        //Cenário
        Usuario usuario = Usuario.builder()
                .username("User")
                .senha("senha")
                .nivel("SA")
                .build();
        Usuario usuarioRetorno = usuarioService.salvar(usuario);

        //Ação
        usuarioService.remover(usuarioRetorno);

        //Verificação
        Optional<Usuario> usuarioTemp = usuarioRepo.findById(usuarioRetorno.getUsuarioId());
        Assertions.assertNotNull(usuarioTemp);
        Assertions.assertFalse(usuarioTemp.isPresent());
    }

    @Test
    public void deveRemoverComFeedback(){
        //Cenário
        Usuario usuario = Usuario.builder()
                .username("User")
                .senha("senha")
                .nivel("SA")
                .build();
        Usuario usuarioRetorno = usuarioService.salvar(usuario);

        //Ação
        Usuario feedback = usuarioService.removerComFeedback(usuarioRetorno.getUsuarioId());

        //Verificação
        Assertions.assertNotNull(feedback);
        Assertions.assertEquals(feedback.getUsuarioId(), usuarioRetorno.getUsuarioId());
    }

    @Test
    public void deveBuscarPorId(){
        //Cenário
        Usuario usuario = Usuario.builder()
                .username("User")
                .senha("senha")
                .nivel("SA")
                .build();
        Usuario usuarioRetorno = usuarioService.salvar(usuario);

        //Ação
        Usuario usuarioBuscado = usuarioService.buscarPorId(usuarioRetorno.getUsuarioId());

        //Verificação
        Assertions.assertNotNull(usuarioBuscado);
        Assertions.assertEquals(usuarioBuscado.getUsuarioId(), usuarioRetorno.getUsuarioId());

        //Rollback
        usuarioRepo.delete(usuarioRetorno);
    }

}

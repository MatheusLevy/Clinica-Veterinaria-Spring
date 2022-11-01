package com.produtos.apirest.Model;

import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.repository.UsuarioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsuarioTeste {

    @Autowired
    public UsuarioRepo usuarioRepo;

    protected static Usuario generateUsuario(){
        return Usuario.builder()
                .username("username")
                .password("password")
                .build();
    }

    private void rollback(Usuario usuario){
        usuarioRepo.delete(usuario);
    }

    protected static void rollbackUsuario(Usuario usuario, UsuarioRepo usuarioRepo){
        usuarioRepo.delete(usuario);
    }

    @Test
    public void deveSalvar(){
        Usuario usuarioSalvo = usuarioRepo.save(generateUsuario());
        Assertions.assertNotNull(usuarioSalvo);
        Assertions.assertNotNull(usuarioSalvo.getUserId());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveAtualizar(){
        Usuario usuarioSalvo = usuarioRepo.save(generateUsuario());
        usuarioSalvo.setUsername("Novo Username");
        Usuario usuarioAtualizado = usuarioRepo.save(usuarioSalvo);
        Assertions.assertNotNull(usuarioAtualizado);
        Assertions.assertEquals(usuarioAtualizado.getUserId(), usuarioSalvo.getUserId());
        Assertions.assertEquals(usuarioAtualizado.getUsername(), "Novo Username");
        rollback(usuarioSalvo);
    }

    @Test
    public void deveRemover(){
        Usuario usuarioSalvo = usuarioRepo.save(generateUsuario());
        Long id = usuarioSalvo.getUserId();
        usuarioRepo.deleteById(id);
        Assertions.assertFalse(usuarioRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscar(){
        Usuario usuarioSalvo = usuarioRepo.save(generateUsuario());
        Long id = usuarioSalvo.getUserId();
        Assertions.assertTrue(usuarioRepo.findById(id).isPresent());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveBuscarPeloUsername(){
        Usuario usuarioSalvo = usuarioRepo.save(generateUsuario());
        Usuario usuarioEncontrado = usuarioRepo.findByUsername(usuarioSalvo.getUsername());
        Assertions.assertNotNull(usuarioEncontrado);
        Assertions.assertEquals(usuarioEncontrado.getUserId(), usuarioSalvo.getUserId());
        rollback(usuarioSalvo);
    }
}
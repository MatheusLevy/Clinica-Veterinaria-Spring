package com.produtos.apirest.Model;

import com.produtos.apirest.models.User;
import com.produtos.apirest.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsuarioTeste {

    @Autowired
    public UserRepo userRepo;

    protected static User generateUsuario(){
        return User.builder()
                .username("username")
                .password("password")
                .build();
    }

    private void rollback(User usuario){
        userRepo.delete(usuario);
    }

    protected static void rollbackUsuario(User usuario, UserRepo userRepo){
        userRepo.delete(usuario);
    }

    @Test
    public void deveSalvar(){
        User usuarioSalvo = userRepo.save(generateUsuario());
        Assertions.assertNotNull(usuarioSalvo);
        Assertions.assertNotNull(usuarioSalvo.getUserId());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveAtualizar(){
        User usuarioSalvo = userRepo.save(generateUsuario());
        usuarioSalvo.setUsername("Novo Username");
        User usuarioAtualizado = userRepo.save(usuarioSalvo);
        Assertions.assertNotNull(usuarioAtualizado);
        Assertions.assertEquals(usuarioAtualizado.getUserId(), usuarioSalvo.getUserId());
        Assertions.assertEquals(usuarioAtualizado.getUsername(), "Novo Username");
        rollback(usuarioSalvo);
    }

    @Test
    public void deveRemover(){
        User usuarioSalvo = userRepo.save(generateUsuario());
        Long id = usuarioSalvo.getUserId();
        userRepo.deleteById(id);
        Assertions.assertFalse(userRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscar(){
        User usuarioSalvo = userRepo.save(generateUsuario());
        Long id = usuarioSalvo.getUserId();
        Assertions.assertTrue(userRepo.findById(id).isPresent());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveBuscarPeloUsername(){
        User usuarioSalvo = userRepo.save(generateUsuario());
        User usuarioEncontrado = userRepo.findByUsername(usuarioSalvo.getUsername());
        Assertions.assertNotNull(usuarioEncontrado);
        Assertions.assertEquals(usuarioEncontrado.getUserId(), usuarioSalvo.getUserId());
        rollback(usuarioSalvo);
    }
}
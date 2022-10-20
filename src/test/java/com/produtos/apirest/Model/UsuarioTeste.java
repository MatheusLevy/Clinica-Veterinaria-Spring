package com.produtos.apirest.Model;

import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.repository.UsuarioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.produtos.apirest.Model.RoleTeste.getRoleListInstance;

@SpringBootTest
public class UsuarioTeste {

    @Autowired
    public UsuarioRepo usuarioRepo;

    protected static Usuario getUsuarioInstance(Boolean temId){
        Usuario usuario = Usuario.builder()
                .username("username")
                .senha("123")
                .roles(getRoleListInstance())
                .build();
        if (temId)
            usuario.setUsuarioId(Long.valueOf(1));
        return usuario;
    }

    @Test
    public void deveSalvarModel(){
        Usuario usuarioSalvo = usuarioRepo.save(getUsuarioInstance(false));
        Assertions.assertNotNull(usuarioSalvo);
        Assertions.assertNotNull(usuarioSalvo.getUsuarioId());
        usuarioRepo.delete(usuarioSalvo);
    }

    @Test
    public void deveRemoverModel(){
        Usuario usuarioSalvo = usuarioRepo.save(getUsuarioInstance(false));
        Long id = usuarioSalvo.getUsuarioId();
        usuarioRepo.deleteById(id);
        Assertions.assertFalse(usuarioRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscarModel(){
        Usuario usuarioSalvo = usuarioRepo.save(getUsuarioInstance(false));
        Long id = usuarioSalvo.getUsuarioId();
        Assertions.assertTrue(usuarioRepo.findById(id).isPresent());
        usuarioRepo.delete(usuarioSalvo);
    }

    @Test
    public void deveBuscarPeloUsernameModel(){
        Usuario usuarioSalvo = usuarioRepo.save(getUsuarioInstance(false));
        Usuario usuarioEncontrado = usuarioRepo.findByUsername(usuarioSalvo.getUsername());
        Assertions.assertNotNull(usuarioEncontrado);
        Assertions.assertEquals(usuarioEncontrado.getUsuarioId(), usuarioSalvo.getUsuarioId());
        usuarioRepo.delete(usuarioSalvo);
    }

    @Test
    public void deveAtualizarUsuario(){
        Usuario usuarioSalvo = usuarioRepo.save(getUsuarioInstance(false));
        usuarioSalvo.setUsername("Novo Username");
        Usuario usuarioAtualizado = usuarioRepo.save(usuarioSalvo);
        Assertions.assertNotNull(usuarioAtualizado);
        Assertions.assertEquals(usuarioAtualizado.getUsuarioId(), usuarioSalvo.getUsuarioId());
        Assertions.assertEquals(usuarioAtualizado.getUsername(), "Novo Username");
        usuarioRepo.delete(usuarioAtualizado);
    }
}
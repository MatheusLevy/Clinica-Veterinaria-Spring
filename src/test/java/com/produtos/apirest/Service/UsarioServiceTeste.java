package com.produtos.apirest.Service;

import com.produtos.apirest.models.User;
import com.produtos.apirest.repository.RoleRepo;
import com.produtos.apirest.repository.UserRepo;
import com.produtos.apirest.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.produtos.apirest.Service.RoleServiceTeste.genereteRolesList;
import static com.produtos.apirest.Service.RoleServiceTeste.rollbackRolesList;

@SpringBootTest
public class UsarioServiceTeste {

    @Autowired
    public RoleRepo roleRepo;

    @Autowired
    public UserRepo userRepo;

    @Autowired
    public UsuarioService usuarioService;

    protected static User generateUsuario(Boolean initializeRole, RoleRepo roleRepo){
        return User.builder()
                .username("test")
                .password("password")
                .roles(genereteRolesList(initializeRole, roleRepo))
                .build();
    }

    private User generateUsuario(Boolean initializeRole){
        return User.builder()
                .username("test")
                .password("password")
                .roles(genereteRolesList(initializeRole, roleRepo))
                .build();
    }

    private void rollback(User usuario){
        userRepo.delete(usuario);
        rollbackRolesList(usuario.getRoles(), roleRepo);
    }

    protected void rollbackUsuario(User usuario, RoleRepo roleRepo){
        userRepo.delete(usuario);
        rollbackRolesList(usuario.getRoles(), roleRepo);
    }

    @Test
    public void deveSalvar(){
        User usuarioSalvo = usuarioService.save(generateUsuario(true));
        Assertions.assertNotNull(usuarioSalvo);
        Assertions.assertNotNull(usuarioSalvo.getUserId());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveAtualizar(){
        User usuarioSalvo = usuarioService.save(generateUsuario(true));
        usuarioSalvo.setUsername("Novo Username");
        User usuarioAtualizado = usuarioService.update(usuarioSalvo);
        Assertions.assertNotNull(usuarioAtualizado);
        Assertions.assertEquals(usuarioAtualizado.getUserId(), usuarioSalvo.getUserId());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveAutenticar(){
        User usuarioSalvo = usuarioService.save(generateUsuario(true));
        User autenticado = usuarioService.authenticate(usuarioSalvo.getUsername(), "password");
        Assertions.assertNotNull(autenticado.getUserId());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveRemover(){
        User usuarioSalvo = usuarioService.save(generateUsuario(true));
        Long id = usuarioSalvo.getUserId();
        usuarioService.remove(usuarioSalvo);
        Assertions.assertFalse(userRepo.findById(id).isPresent());
        rollbackRolesList(usuarioSalvo.getRoles(), roleRepo);
    }

    @Test
    public void deveRemoverComFeedback(){
        User usuarioSalvo = usuarioService.save(generateUsuario(true));
        User usuarioFeeback = usuarioService.removeByIdWithFeedback(usuarioSalvo.getUserId());
        Assertions.assertNotNull(usuarioFeeback);
        Assertions.assertEquals(usuarioFeeback.getUserId(), usuarioSalvo.getUserId());
        rollbackRolesList(usuarioSalvo.getRoles(), roleRepo);
    }

    @Test
    public void deveBuscarPorUsername(){
        User usuarioSalvo = usuarioService.save(generateUsuario(true));
        User usuarioBuscado = usuarioService.findByUsername(usuarioSalvo.getUsername());
        Assertions.assertNotNull(usuarioBuscado);
        Assertions.assertEquals(usuarioBuscado.getUserId(), usuarioSalvo.getUserId());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveBuscarPorId(){
        User usuarioSalvo = usuarioService.save(generateUsuario(true));
        Long id = usuarioSalvo.getUserId();
        User usuarioBuscado = usuarioService.findById(id);
        Assertions.assertNotNull(usuarioBuscado);
        Assertions.assertEquals(usuarioBuscado.getUserId(), usuarioSalvo.getUserId());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveBuscarTodos(){
        User usuarioSalvo = usuarioService.save(generateUsuario(true));
        Assertions.assertFalse(usuarioService.findAll().isEmpty());
        rollback(usuarioSalvo);
    }
}
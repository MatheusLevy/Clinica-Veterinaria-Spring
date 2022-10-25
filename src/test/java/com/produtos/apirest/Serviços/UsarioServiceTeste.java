package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.repository.RoleRepo;
import com.produtos.apirest.repository.UsuarioRepo;
import com.produtos.apirest.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.produtos.apirest.Serviços.RoleServiceTeste.genereteRolesList;
import static com.produtos.apirest.Serviços.RoleServiceTeste.rollbackRolesList;

@SpringBootTest
public class UsarioServiceTeste {

    @Autowired
    public RoleRepo roleRepo;

    @Autowired
    public UsuarioRepo usuarioRepo;

    @Autowired
    public UsuarioService usuarioService;

    protected static Usuario generateUsuario(Boolean initializeRole, RoleRepo roleRepo){
        return Usuario.builder()
                .username("teste")
                .senha("senha")
                .roles(genereteRolesList(initializeRole, roleRepo))
                .build();
    }

    private Usuario generateUsuario(Boolean initializeRole){
        return Usuario.builder()
                .username("teste")
                .senha("senha")
                .roles(genereteRolesList(initializeRole, roleRepo))
                .build();
    }

    private void rollback(Usuario usuario){
        usuarioRepo.delete(usuario);
        rollbackRolesList(usuario.getRoles(), roleRepo);
    }

    protected void rollbackUsuario(Usuario usuario, RoleRepo roleRepo){
        usuarioRepo.delete(usuario);
        rollbackRolesList(usuario.getRoles(), roleRepo);
    }

    @Test
    public void deveSalvar(){
        Usuario usuarioSalvo = usuarioService.salvar(generateUsuario(true));
        Assertions.assertNotNull(usuarioSalvo);
        Assertions.assertNotNull(usuarioSalvo.getUsuarioId());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveAtualizar(){
        Usuario usuarioSalvo = usuarioService.salvar(generateUsuario(true));
        usuarioSalvo.setUsername("Novo Username");
        Usuario usuarioAtualizado = usuarioService.atualizar(usuarioSalvo);
        Assertions.assertNotNull(usuarioAtualizado);
        Assertions.assertEquals(usuarioAtualizado.getUsuarioId(), usuarioSalvo.getUsuarioId());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveAutenticar(){
        Usuario usuarioSalvo = usuarioService.salvar(generateUsuario(true));
        Usuario autenticado = usuarioService.autenticar(usuarioSalvo.getUsername(), "senha");
        Assertions.assertNotNull(autenticado.getUsuarioId());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveRemover(){
        Usuario usuarioSalvo = usuarioService.salvar(generateUsuario(true));
        Long id = usuarioSalvo.getUsuarioId();
        usuarioService.remover(usuarioSalvo);
        Assertions.assertFalse(usuarioRepo.findById(id).isPresent());
        rollbackRolesList(usuarioSalvo.getRoles(), roleRepo);
    }

    @Test
    public void deveRemoverComFeedback(){
        Usuario usuarioSalvo = usuarioService.salvar(generateUsuario(true));
        Usuario usuarioFeeback = usuarioService.removerComFeedback(usuarioSalvo.getUsuarioId());
        Assertions.assertNotNull(usuarioFeeback);
        Assertions.assertEquals(usuarioFeeback.getUsuarioId(), usuarioSalvo.getUsuarioId());
        rollbackRolesList(usuarioSalvo.getRoles(), roleRepo);
    }

    @Test
    public void deveBuscarPorUsername(){
        Usuario usuarioSalvo = usuarioService.salvar(generateUsuario(true));
        Usuario usuarioBuscado = usuarioService.buscarPorUsername(usuarioSalvo.getUsername());
        Assertions.assertNotNull(usuarioBuscado);
        Assertions.assertEquals(usuarioBuscado.getUsuarioId(), usuarioSalvo.getUsuarioId());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveBuscarPorId(){
        Usuario usuarioSalvo = usuarioService.salvar(generateUsuario(true));
        Long id = usuarioSalvo.getUsuarioId();
        Usuario usuarioBuscado = usuarioService.buscarPorId(id);
        Assertions.assertNotNull(usuarioBuscado);
        Assertions.assertEquals(usuarioBuscado.getUsuarioId(), usuarioSalvo.getUsuarioId());
        rollback(usuarioSalvo);
    }

    @Test
    public void deveBuscarTodos(){
        Usuario usuarioSalvo = usuarioService.salvar(generateUsuario(true));
        Assertions.assertFalse(usuarioService.buscarTodos().isEmpty());
        rollback(usuarioSalvo);
    }
}
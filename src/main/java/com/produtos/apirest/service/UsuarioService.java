package com.produtos.apirest.service;

import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.repository.UsuarioRepo;
import com.produtos.apirest.service.excecoes.AuthenticationFailedExpection;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepo usuarioRepo;

    public UsuarioService(UsuarioRepo usuarioRepo){
        this.usuarioRepo = usuarioRepo;
    }

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void verificaUsuario(Usuario usuario){
        if(usuario == null)
            throw new NullPointerException("Usuario não pode ser nulo!");
        if (usuario.getUsername() == null)
            throw  new RegraNegocioRunTime("Nome de usuário não pode ser nulo!");
        if (usuario.getPassword().equals(""))
            throw new RegraNegocioRunTime("Senha não pode ser vazia!");
        if (usuario.getRoles().isEmpty())
            throw new RegraNegocioRunTime("As roles do usuário não podem ser nulas!");
    }

    public static void verificaId(Usuario usuario){
        if (usuario == null || usuario.getUserId() <= 0){
            throw new RegraNegocioRunTime("Usuario deve possuir um identificador!");
        }
    }

    public static void verificaId(Long id){
        if (id <= 0){
            throw new RegraNegocioRunTime("Usuario deve possuir um identificador!");
        }
    }

    @Transactional
    public Usuario autenticar (String username, String senha){
        Usuario usuario = usuarioRepo.findByUsername(username);
        if(passwordEncoder().matches(senha, usuario.getPassword()))
            return usuario;
        else
            throw new AuthenticationFailedExpection("Usuário ou senha inválidos!");
    }

    @Transactional
    public Usuario salvar(Usuario usuario){
        verificaUsuario(usuario);
        usuario.setPassword(passwordEncoder().encode(usuario.getPassword()));
        return usuarioRepo.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Usuario usuario){
        verificaUsuario(usuario);
        verificaId(usuario);
        return usuarioRepo.save(usuario);
    }

    @Transactional
    public void remover(Usuario usuario){
        verificaUsuario(usuario);
        verificaId(usuario);
        usuarioRepo.delete(usuario);
    }

    @Transactional
    public Usuario removerComFeedback(Long id){
        verificaId(id);
        Optional<Usuario> usuariosEncontrados = usuarioRepo.findById(id);
        if (usuariosEncontrados.isPresent()) {
            Usuario usuarioFeedback = usuariosEncontrados.get();
            usuarioRepo.delete(usuarioFeedback);
            return usuarioFeedback;
        }
        return null;
    }

    @Transactional
    public Usuario buscarPorId(Long id){
        verificaId(id);
        Optional<Usuario> usuariosEncontrados = usuarioRepo.findById(id);
        return usuariosEncontrados.orElse(null);
    }

    @Transactional
    public Usuario buscarPorUsername(String username){
        return usuarioRepo.findByUsername(username);
    }

    @Transactional
    public List<Usuario> buscarTodos(){
        return usuarioRepo.findAll();
    }
}
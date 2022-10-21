package com.produtos.apirest.service;

import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.repository.UsuarioRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void verificaUsuario(Usuario usuario){
        if(usuario == null)
            throw new NullPointerException("Usuario não pode ser nulo!");
        if (usuario.getUsername() == null)
            throw  new RegraNegocioRunTime("Nome de usuário não pode ser nulo!");
        if (usuario.getSenha() == null)
            throw new RegraNegocioRunTime("Senha não pode ser nula!");
        if (usuario.getRoles().isEmpty())
            throw new RegraNegocioRunTime("As roles do usuário não podem ser nulas!");
    }

    public static void verificaId(Usuario usuario){
        if (usuario == null || Long.valueOf(usuario.getUsuarioId()) == null){
            throw new RegraNegocioRunTime("Usuario deve possuir um identificador!");
        }
    }

    public static void verificaId(Long id){
        if (Long.valueOf(id) == null){
            throw new RegraNegocioRunTime("Usuario deve possuir um identificador!");
        }
    }

    @Transactional
    public Usuario autenticar (String username, String senha){
        Usuario usuario = usuarioRepo.findByUsername(username);
        if(passwordEncoder().matches(senha, usuario.getSenha()))
            return usuario;
        else
            return new Usuario();
    }

    @Transactional
    public Usuario buscarPorUsername(String username){
        return usuarioRepo.findByUsername(username);
    }

    @Transactional
    public Usuario salvar(Usuario usuario){
        verificaUsuario(usuario);
        usuario.setSenha(passwordEncoder().encode(usuario.getSenha()));
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
        Usuario usuarioFeedback = usuarioRepo.findById(id).get();
        usuarioRepo.delete(usuarioFeedback);
        return usuarioFeedback;
    }

    @Transactional
    public Usuario buscarPorId(Long id){
        verificaId(id);
        return usuarioRepo.findById(id).get();
    }

    @Transactional
    public List<Usuario> buscarTodos(){
        return usuarioRepo.findAll();
    }
}
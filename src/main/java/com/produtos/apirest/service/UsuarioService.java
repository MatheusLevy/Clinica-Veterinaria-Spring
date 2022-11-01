package com.produtos.apirest.service;

import com.produtos.apirest.models.User;
import com.produtos.apirest.repository.UserRepo;
import com.produtos.apirest.service.excecoes.AuthenticationFailedExpection;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UserRepo userRepo;

    public UsuarioService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void verificaUsuario(User usuario){
        if(usuario == null)
            throw new NullPointerException("Usuario não pode ser nulo!");
        if (usuario.getUsername() == null)
            throw  new RegraNegocioRunTime("Nome de usuário não pode ser nulo!");
        if (usuario.getPassword().equals(""))
            throw new RegraNegocioRunTime("Senha não pode ser vazia!");
        if (usuario.getRoles().isEmpty())
            throw new RegraNegocioRunTime("As roles do usuário não podem ser nulas!");
    }

    public static void verificaId(User usuario){
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
    public User autenticar (String username, String senha){
        User usuario = userRepo.findByUsername(username);
        if(passwordEncoder().matches(senha, usuario.getPassword()))
            return usuario;
        else
            throw new AuthenticationFailedExpection("Usuário ou senha inválidos!");
    }

    @Transactional
    public User salvar(User usuario){
        verificaUsuario(usuario);
        usuario.setPassword(passwordEncoder().encode(usuario.getPassword()));
        return userRepo.save(usuario);
    }

    @Transactional
    public User atualizar(User usuario){
        verificaUsuario(usuario);
        verificaId(usuario);
        return userRepo.save(usuario);
    }

    @Transactional
    public void remover(User usuario){
        verificaUsuario(usuario);
        verificaId(usuario);
        userRepo.delete(usuario);
    }

    @Transactional
    public User removerComFeedback(Long id){
        verificaId(id);
        Optional<User> usuariosEncontrados = userRepo.findById(id);
        if (usuariosEncontrados.isPresent()) {
            User usuarioFeedback = usuariosEncontrados.get();
            userRepo.delete(usuarioFeedback);
            return usuarioFeedback;
        }
        return null;
    }

    @Transactional
    public User buscarPorId(Long id){
        verificaId(id);
        Optional<User> usuariosEncontrados = userRepo.findById(id);
        return usuariosEncontrados.orElse(null);
    }

    @Transactional
    public User buscarPorUsername(String username){
        return userRepo.findByUsername(username);
    }

    @Transactional
    public List<User> buscarTodos(){
        return userRepo.findAll();
    }
}
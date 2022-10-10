package com.produtos.apirest.service;

import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsarioService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    //TODO: **Verificar outras formas de Criptograr
    // - [ ] SHA256
    // - [ ] MD5
    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public static void verificaUsuario(Usuario usuario){
        if(usuario == null)
            throw new NullPointerException("Usuario não pode ser nulo!");
        if (usuario.getUsername() == null)
            throw  new NullPointerException("Nome de usuário não pode ser nulo!");
        if (usuario.getSenha() == null)
            throw new NullPointerException("Senha não pdoe ser nula!");
        if (usuario.getNivel() == null)
            throw new NullPointerException("Nivel de usuário não pode ser nulo!");
    }

    public static void verificaId(Usuario usuario){
        if (usuario == null || Long.valueOf(usuario.getUsuarioId()) == null){
            throw new NullPointerException("Identificador de Usuário inválido!");
        }
    }

    @Transactional
    public Usuario autenticar (Usuario usuario){
        Example<Usuario> example = Example.of(usuario, ExampleMatcher.matching()
                .withIgnoreCase());
        List<Usuario> usarioList =  usuarioRepo.findAll(example);
        if (usarioList.isEmpty()){
            return new Usuario();
        }
        return usarioList.get(0);
    }

    @Transactional
    public List<Usuario> buscar(Usuario filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<Usuario> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return usuarioRepo.findAll(example);
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
    public Usuario removerComFeedback(Usuario usuario){
        verificaUsuario(usuario);
        verificaId(usuario);
        Optional<Usuario> usariosList = usuarioRepo.findById(usuario.getUsuarioId());
        Usuario usuarioRemovido = usariosList.get();
        usuarioRepo.delete(usuario);
        return usuarioRemovido;
    }

    @Transactional
    public Usuario buscarPorId(Usuario usuario){
        verificaId(usuario);
        return usuarioRepo.findById(usuario.getUsuarioId()).get();
    }

    @Transactional
    public List<Usuario> buscarTodos(){
        return usuarioRepo.findAll();
    }

}

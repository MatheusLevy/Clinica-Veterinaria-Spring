package com.produtos.apirest.autenticacao;

import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.repository.UsuarioRepo;
import com.produtos.apirest.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuarioFind = usuarioRepo.findByUsername(username);
        UsuarioService.verificaUsuario(usuarioFind);
        UsuarioService.verificaId(usuarioFind);
        return new User(usuarioFind.getUsername(), usuarioFind.getPassword(), true, true, true, true, usuarioFind.getAuthorities());
    }
}

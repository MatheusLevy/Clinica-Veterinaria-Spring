package com.produtos.apirest.autenticacao;

import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.repository.UsuarioRepo;
import com.produtos.apirest.service.UsarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuarioFind = usuarioRepo.findByUsername(username);
        UsarioService.verificaUsuario(usuarioFind);
        UsarioService.verificaId(usuarioFind);

        return UserPrincipal.create(usuarioFind);
    }
}

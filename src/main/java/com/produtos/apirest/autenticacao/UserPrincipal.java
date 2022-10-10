package com.produtos.apirest.autenticacao;

import com.produtos.apirest.models.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    //TODO: **Modificar a role do usuário para uma List
    private UserPrincipal(Usuario usuario){
        this.username = usuario.getUsername();
        this.password = usuario.getSenha();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        char[] characterArray = usuario.getNivel().toCharArray();
        for(char c : characterArray)
            authorities.add(new SimpleGrantedAuthority("ROLE_"+c));
        this.authorities = authorities;
    }

    public static UserPrincipal create(Usuario usuario){
        return new UserPrincipal(usuario);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

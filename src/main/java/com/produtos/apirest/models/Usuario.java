package com.produtos.apirest.models;

import com.produtos.apirest.models.DTO.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long usuarioId;

    @Column(name = "usuario")
    private String username;

    @Column(name = "senha")
    private String senha;

    @ManyToMany
    @JoinTable(name="Users_Roles",
            joinColumns= @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + usuarioId + ", username= " + username
                + ", senha= " + senha + ", Roles= " + roles + "]";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername(){
        return this.username;
    }
    @Override
    public String getPassword() {
        return this.senha;
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

    public UsuarioDTO toUsuarioDTO(){
        return UsuarioDTO.builder()
                .id(this.usuarioId)
                .username(this.username)
                .roles(this.roles)
                .build();
    }
}
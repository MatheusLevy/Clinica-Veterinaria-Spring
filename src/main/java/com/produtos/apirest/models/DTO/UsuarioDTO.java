package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Role;
import com.produtos.apirest.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String username;
    private String senha;
    private List<Role> roles;

    public Usuario toUsuario(){
        return Usuario.builder()
                .usuarioId(this.id)
                .username(this.username)
                .senha(this.senha)
                .roles(this.roles)
                .build();
    }
}
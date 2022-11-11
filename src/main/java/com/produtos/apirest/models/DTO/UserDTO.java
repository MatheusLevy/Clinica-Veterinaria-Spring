package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Role;
import com.produtos.apirest.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank
    private String username;
    private String password;
    private List<Role> roles;

    private void hasId(){
        if (this.id == null)
            this.id = 0L;
    }

    private void hasPassword(){
        if (this.password == null)
            this.password = "";
    }

    private void hasAllFieldsNotNull(){
        hasId();
        hasPassword();
    }

    public User toUser(){
        hasAllFieldsNotNull();
        return User.builder()
                .userId(this.id)
                .username(this.username)
                .password(this.password)
                .roles(this.roles)
                .build();
    }
}
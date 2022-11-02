package com.produtos.apirest.models.DTO;

import com.produtos.apirest.enums.RoleName;
import com.produtos.apirest.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private RoleName roleName;

    public Role toRole(){
        return Role.builder()
                .roleId(this.id)
                .roleName(this.roleName)
                .build();
    }
}
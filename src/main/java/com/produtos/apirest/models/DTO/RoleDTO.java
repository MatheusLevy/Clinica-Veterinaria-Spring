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

    private void hasId(){
        if (this.id == null)
            this.id = 0L;
    }

    public Role toRole(){
        hasId();
        return Role.builder()
                .roleId(this.id)
                .roleName(this.roleName)
                .build();
    }
}
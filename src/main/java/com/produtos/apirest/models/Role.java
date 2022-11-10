package com.produtos.apirest.models;

import com.produtos.apirest.enums.RoleName;
import com.produtos.apirest.models.DTO.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "role")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "roleName", nullable = false, unique = true)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return this.roleName.toString();
    }

    public RoleDTO toRoleDTO(){
        return RoleDTO.builder()
                .id(this.roleId)
                .roleName(this.roleName)
                .build();
    }
}
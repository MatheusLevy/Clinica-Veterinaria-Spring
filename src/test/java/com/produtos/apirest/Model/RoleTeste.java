package com.produtos.apirest.Model;

import com.produtos.apirest.enums.RoleName;
import com.produtos.apirest.models.Role;
import com.produtos.apirest.repository.RoleRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class RoleTeste {

    @Autowired
    public RoleRepo roleRepo;

    protected static Role getRoleInstance(Boolean temId){
        Role role = Role.builder()
                .roleName(RoleName.ROLE_TESTE)
                .build();
        if (temId)
            role.setRoleId(Long.valueOf(1));
        return role;
    }

    protected static List<Role> getRoleListInstance(){
        return new ArrayList<>(){{
            add(getRoleInstance(true));
        }};
    }

    @Test
    public void deveSalvarModel(){
        Role roleSalva= roleRepo.save(getRoleInstance(false));
        Assertions.assertNotNull(roleSalva);
        Assertions.assertEquals(getRoleInstance(false).getRoleName(), roleSalva.getRoleName());
        roleRepo.delete(roleSalva);
    }

    @Test
    public void deveRemoverModel(){
        Role roleSalva= roleRepo.save(getRoleInstance(false));
        Long id = roleSalva.getRoleId();
        roleRepo.deleteById(id);
        Assertions.assertFalse(roleRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscarModel(){
        Role roleSalva= roleRepo.save(getRoleInstance(false));
        Long id = roleSalva.getRoleId();
        Assertions.assertTrue(roleRepo.findById(id).isPresent());
        roleRepo.delete(roleSalva);
    }

    @Test
    public void deveAtualizarModel(){
        Role roleSalva= roleRepo.save(getRoleInstance(false));
        roleSalva.setRoleName(RoleName.ROLE_TESTE2);
        Role roleAtualizada = roleRepo.save(roleSalva);
        Assertions.assertNotNull(roleAtualizada);
        Assertions.assertEquals(roleAtualizada.getRoleId(), roleSalva.getRoleId());
        Assertions.assertEquals(roleAtualizada.getRoleName(), RoleName.ROLE_TESTE2);
        roleRepo.delete(roleAtualizada);
    }
}

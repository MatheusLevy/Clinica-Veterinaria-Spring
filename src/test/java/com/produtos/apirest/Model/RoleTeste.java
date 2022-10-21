package com.produtos.apirest.Model;

import com.produtos.apirest.enums.RoleName;
import com.produtos.apirest.models.Role;
import com.produtos.apirest.repository.RoleRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleTeste {

    @Autowired
    public RoleRepo roleRepo;

    protected static Role generateRole(){
        return Role.builder()
                .roleName(RoleName.ROLE_TESTE)
                .build();
    }

    private void rollback(Role role){
        roleRepo.delete(role);
    }

    protected static void rollbackRole(Role role, RoleRepo roleRepo){
        roleRepo.delete(role);
    }

    @Test
    public void deveSalvarModel(){
        Role roleSalva= roleRepo.save(generateRole());
        Assertions.assertNotNull(roleSalva);
        Assertions.assertEquals(generateRole().getRoleName(), roleSalva.getRoleName());
        rollback(roleSalva);
    }

    @Test
    public void deveAtualizarModel(){
        Role roleSalva= roleRepo.save(generateRole());
        roleSalva.setRoleName(RoleName.ROLE_TESTE2);
        Role roleAtualizada = roleRepo.save(roleSalva);
        Assertions.assertNotNull(roleAtualizada);
        Assertions.assertEquals(roleAtualizada.getRoleId(), roleSalva.getRoleId());
        Assertions.assertEquals(roleAtualizada.getRoleName(), RoleName.ROLE_TESTE2);
        rollback(roleAtualizada);
    }

    @Test
    public void deveRemoverModel(){
        Role roleSalva= roleRepo.save(generateRole());
        Long id = roleSalva.getRoleId();
        roleRepo.deleteById(id);
        Assertions.assertFalse(roleRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscarModel(){
        Role roleSalva= roleRepo.save(generateRole());
        Long id = roleSalva.getRoleId();
        Assertions.assertTrue(roleRepo.findById(id).isPresent());
        rollback(roleSalva);
    }
}
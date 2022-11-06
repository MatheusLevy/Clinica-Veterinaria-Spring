package com.produtos.apirest.Model;

import com.produtos.apirest.enums.RoleName;
import com.produtos.apirest.models.Role;
import com.produtos.apirest.repository.RoleRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleTest {

    @Autowired
    public RoleRepo roleRepo;

    protected static Role generateRole(){
        return Role.builder()
                .roleName(RoleName.ROLE_TEST)
                .build();
    }

    private void rollback(Role role){
        roleRepo.delete(role);
    }

    protected static void rollbackRole(Role role, RoleRepo roleRepo){
        roleRepo.delete(role);
    }

    @Test
    public void save(){
        Role roleSaved = roleRepo.save(generateRole());
        Assertions.assertNotNull(roleSaved);
        Assertions.assertEquals(generateRole().getRoleName(), roleSaved.getRoleName());
        rollback(roleSaved);
    }

    @Test
    public void update(){
        Role roleSaved = roleRepo.save(generateRole());
        roleSaved.setRoleName(RoleName.ROLE_TEST2);
        Role roleUpdated = roleRepo.save(roleSaved);
        Assertions.assertNotNull(roleUpdated);
        Assertions.assertEquals(roleUpdated.getRoleId(), roleSaved.getRoleId());
        Assertions.assertEquals(roleUpdated.getRoleName(), RoleName.ROLE_TEST2);
        rollback(roleUpdated);
    }

    @Test
    public void removeById(){
        Role roleSaved = roleRepo.save(generateRole());
        Long id = roleSaved.getRoleId();
        roleRepo.deleteById(id);
        Assertions.assertNull(roleRepo.findByRoleId(id));
    }

    @Test
    public void findById(){
        Role roleSaved = roleRepo.save(generateRole());
        Long id = roleSaved.getRoleId();
        Assertions.assertNotNull(roleRepo.findByRoleId(id));
        rollback(roleSaved);
    }
}
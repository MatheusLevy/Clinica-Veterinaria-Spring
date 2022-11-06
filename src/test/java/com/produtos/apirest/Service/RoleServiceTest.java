package com.produtos.apirest.Service;

import com.produtos.apirest.enums.RoleName;
import com.produtos.apirest.models.Role;
import com.produtos.apirest.repository.RoleRepo;
import com.produtos.apirest.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RoleServiceTest {

    @Autowired
    public RoleService roleService;

    @Autowired
    public RoleRepo roleRepo;

    protected static Role generateRole(Boolean initRole, RoleRepo roleRepo){
        Role role = Role.builder()
                .roleName(RoleName.ROLE_TEST)
                .build();
        if (initRole)
            role = roleRepo.save(role);
        return role;
    }

    private Role generateRole(){
        return Role.builder()
                .roleName(RoleName.ROLE_TEST)
                .build();
    }

    protected static List<Role> generateRolesList(Boolean initRole, RoleRepo roleRepo){
        return new ArrayList<>(){{
            add(generateRole(initRole, roleRepo));
            }};
    }

    private void rollback(Role role){
        roleRepo.delete(role);
    }

    protected static void rollbackRole(Role role, RoleRepo roleRepo){
        roleRepo.delete(role);
    }

    protected static void rollbackRolesList(List<Role> roles, RoleRepo roleRepo){
        for (Role role : roles)
            rollbackRole(role, roleRepo);
    }

    @Test
    public void save(){
        Role roleSaved = roleService.save(generateRole());
        Assertions.assertNotNull(roleSaved);
        Assertions.assertNotNull(roleSaved.getRoleId());
        rollback(roleSaved);
    }

    @Test
    public void update(){
        Role roleSaved = roleRepo.save(generateRole());
        roleSaved.setRoleName(RoleName.ROLE_TEST2);
        Role roleUpdated = roleService.update(roleSaved);
        Assertions.assertNotNull(roleUpdated);
        Assertions.assertEquals(roleSaved.getRoleId(), roleUpdated.getRoleId());
        rollback(roleUpdated);
    }

    @Test
    public void remove(){
        Role roleSaved = roleRepo.save(generateRole());
        Long id = roleSaved.getRoleId();
        roleService.remove(roleSaved);
        Assertions.assertFalse(roleRepo.findById(id).isPresent());
    }

    @Test
    public void removeByIdWithFeedback(){
        Role roleSaved = roleRepo.save(generateRole());
        Long id = roleSaved.getRoleId();
        Role feedback = roleService.removeByIdWithFeedback(id);
        Assertions.assertNotNull(feedback);
        Assertions.assertEquals(roleSaved.getRoleId(), feedback.getRoleId());
    }

    @Test
    public void removeById(){
        Role roleSaved = roleRepo.save(generateRole());
        Long id = roleSaved.getRoleId();
        roleService.removeById(id);
        Assertions.assertFalse(roleRepo.findById(id).isPresent());
    }

    @Test
    public void find(){
        Role roleSaved = roleRepo.save(generateRole());
        List<Role> roles = roleService.find(roleSaved);
        Assertions.assertFalse(roles.isEmpty());
        rollback(roleSaved);
    }

    @Test
    public void findById(){
        Role roleSaved = roleRepo.save(generateRole());
        Long id = roleSaved.getRoleId();
        Role roleFind = roleService.findById(id);
        Assertions.assertNotNull(roleFind);
        rollback(roleSaved);
    }

    @Test
    public void findAll(){
        Role roleSaved = roleRepo.save(generateRole());
        List<Role> roles = roleService.buscarTodos();
        Assertions.assertNotNull(roles);
        Assertions.assertFalse(roles.isEmpty());
        rollback(roleSaved);
    }
}
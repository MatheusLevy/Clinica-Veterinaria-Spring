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
public class RoleServiceTeste {

    @Autowired
    public RoleService roleService;

    @Autowired
    public RoleRepo roleRepo;

    protected static Role generateRole(Boolean initializeRole, RoleRepo roleRepo){
        Role role = Role.builder()
                .roleName(RoleName.ROLE_TESTE)
                .build();
        if (initializeRole)
            role = roleRepo.save(role);
        return role;
    }

    private Role generateRole(){
        return Role.builder()
                .roleName(RoleName.ROLE_TESTE)
                .build();
    }

    protected static List<Role> genereteRolesList(Boolean initializeRole, RoleRepo roleRepo){
        return new ArrayList<>(){{
            add(generateRole(initializeRole, roleRepo));
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
    public void deveSalvar(){
        Role roleSalva = roleService.save(generateRole());
        Assertions.assertNotNull(roleSalva);
        Assertions.assertNotNull(roleSalva.getRoleId());
        rollback(roleSalva);
    }

    @Test
    public void deveAtualizar(){
        Role roleSalva = roleRepo.save(generateRole());
        roleSalva.setRoleName(RoleName.ROLE_TESTE2);
        Role roleAtualizada = roleService.update(roleSalva);
        Assertions.assertNotNull(roleAtualizada);
        Assertions.assertEquals(roleSalva.getRoleId(), roleAtualizada.getRoleId());
        rollback(roleAtualizada);
    }

    @Test
    public void deveRemover(){
        Role roleSalva = roleRepo.save(generateRole());
        Long id = roleSalva.getRoleId();
        roleService.remove(roleSalva);
        Assertions.assertFalse(roleRepo.findById(id).isPresent());
    }

    @Test
    public void deveRemoverComFeedback(){
        Role roleSalva = roleRepo.save(generateRole());
        Long id = roleSalva.getRoleId();
        Role roleFeedback = roleService.removeByIdWithFeedback(id);
        Assertions.assertNotNull(roleFeedback);
        Assertions.assertEquals(roleSalva.getRoleId(), roleFeedback.getRoleId());
    }

    @Test
    public void deveRemoverComId(){
        Role roleSalva = roleRepo.save(generateRole());
        Long id = roleSalva.getRoleId();
        roleService.removeById(id);
        Assertions.assertFalse(roleRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscar(){
        Role roleSalva = roleRepo.save(generateRole());
        List<Role> rolesEncontradas = roleService.find(roleSalva);
        Assertions.assertFalse(rolesEncontradas.isEmpty());
        rollback(roleSalva);
    }

    @Test
    public void deveBuscarPorId(){
        Role roleSalva = roleRepo.save(generateRole());
        Long id = roleSalva.getRoleId();
        Role roleEncontrada = roleService.findById(id);
        Assertions.assertNotNull(roleEncontrada);
        rollback(roleSalva);
    }

    @Test
    public void deveBuscarTodos(){
        Role roleSalva = roleRepo.save(generateRole());
        List<Role> rolesList = roleService.buscarTodos();
        Assertions.assertNotNull(rolesList);
        Assertions.assertFalse(rolesList.isEmpty());
        rollback(roleSalva);
    }
}
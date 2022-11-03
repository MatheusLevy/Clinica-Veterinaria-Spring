package com.produtos.apirest.service;

import com.produtos.apirest.models.Role;
import com.produtos.apirest.repository.RoleRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo){
        this.roleRepo = roleRepo;
    }

    public static void isNotNull(Role role){
        if (role == null)
            throw new NullPointerException("Role must not be null!");
    }

    public static void hasId(Role role){
        if (role.getRoleId() <= 0)
            throw new RegraNegocioRunTime("The role should have a id");
    }

    public static void hasId(Long id){
        if (id <= 0)
            throw new RegraNegocioRunTime("The role should have a id");
    }

    public static void verifyAllRules(Role role){
        isNotNull(role);
        hasId(role);
    }

    public static Example<Role> generateExample(Role role){
        return Example.of(role, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    }

    @Transactional
    public Role save(Role role){
        isNotNull(role);
        return roleRepo.save(role);
    }

    @Transactional
    public Role update(Role role){
        verifyAllRules(role);
        return roleRepo.save(role);
    }

    @Transactional
    public void remove(Role role){
        verifyAllRules(role);
        roleRepo.delete(role);
    }

    @Transactional
    public void removeById(Long id){
        hasId(id);
        roleRepo.deleteById(id);
    }

    @Transactional
    public Role removeByIdWithFeedback(Long id){
        hasId(id);
        Role feedback = roleRepo .findByRoleId(id);
        roleRepo.delete(feedback);
        return feedback;
    }

    @Transactional
    public List<Role> find(Role role){
        isNotNull(role);
        Example<Role> example = generateExample(role);
        return roleRepo.findAll(example);
    }

    @Transactional
    public Role findById(Long id){
        hasId(id);
        return roleRepo.findByRoleId(id);
    }

    @Transactional
    public List<Role> buscarTodos(){
        return roleRepo.findAll();
    }
}
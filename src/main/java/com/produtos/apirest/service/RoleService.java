package com.produtos.apirest.service;

import com.produtos.apirest.models.Role;
import com.produtos.apirest.repository.RoleRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo){
        this.roleRepo = roleRepo;
    }

    public static void verificaRole(Role role){
        if (role == null)
            throw new NullPointerException("Role não pode ser Nula!");
        if (role.getRoleName() == null)
            throw new RegraNegocioRunTime("Role deve ter um RoleName!");
    }

    public static void verificaId(Role role){
        if (role.getRoleId() == null)
            throw new RegraNegocioRunTime("Role deve ter um identificador!");
    }

    public static void verificaId(Long id){
        if (id == null || id <= 0)
            throw new RegraNegocioRunTime("Role deve ter um identificador!");
    }

    @Transactional
    public Role salvar(Role role){
        verificaRole(role);
        return roleRepo.save(role);
    }

    @Transactional
    public Role atualizar(Role role){
        verificaRole(role);
        verificaId(role);
        return roleRepo.save(role);
    }

    @Transactional
    public void remover(Role role){
        verificaRole(role);
        verificaId(role);
        roleRepo.delete(role);
    }

    @Transactional
    public void removerPorId(Long id){
        verificaId(id);
        roleRepo.deleteById(id);
    }

    @Transactional
    public Role RemoverComFeedback(Long id){
        verificaId(id);
        Optional<Role> rolesEncontradas = roleRepo .findById(id);
        if (rolesEncontradas.isPresent()) {
            Role roleFeedback = rolesEncontradas.get();
            roleRepo.deleteById(id);
            return roleFeedback;
        }
        return null;
    }

    @Transactional
    public List<Role> buscar(Role filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<Role> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return roleRepo.findAll(example);
    }

    @Transactional
    public Role buscarPorId(Long id){
        verificaId(id);
        Optional<Role> rolesEncontradas = roleRepo.findById(id);
        return rolesEncontradas.orElse(null);
    }

    @Transactional
    public List<Role> buscarTodos(){
        return roleRepo.findAll();
    }
}
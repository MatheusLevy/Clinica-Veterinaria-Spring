package com.produtos.apirest.service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.repository.OwnerRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DonoService {

    private final OwnerRepo repo;

    public DonoService(OwnerRepo ownerRepo){
        this.repo = ownerRepo;
    }

    public static void verifyIsNull(Owner owner){
        if (owner == null)
            throw new NullPointerException("Owner must not be null!");
    }

    public static void verifyHasId(Owner owner){
        if (owner == null || owner.getOwnerId() <= 0)
            throw new RegraNegocioRunTime("The owner should have a id!");
    }

    public static void verifyHasId(Long id){
        if (id <= 0)
            throw new RegraNegocioRunTime("The owner should have a id!");
    }

    public static void verifyHasName(Owner owner){
        if (owner.getName().equals(""))
            throw new RegraNegocioRunTime("The owner should have a name!");
    }

    public static void verifyHasCPF(Owner owner){
        if (owner.getCpf().equals(""))
            throw new RegraNegocioRunTime("The owner should have a CPF!");
    }

    public static void verifyAllRules(Owner owner){
        verifyIsNull(owner);
        verifyHasId(owner);
        verifyHasCPF(owner);
        verifyHasName(owner);
    }

    public static void verifyIsNullHasCPFHasName(Owner owner){
        verifyIsNull(owner);
        verifyHasCPF(owner);
        verifyHasName(owner);
    }

    public static Example<Owner> generateFilter(Owner owner){
        return Example.of(owner, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    }

    @Transactional
    public Owner save(Owner owner){
        verifyIsNullHasCPFHasName(owner);
        return repo.save(owner);
    }

    @Transactional
    public Owner update(Owner owner){
        verifyAllRules(owner);
        return repo.save(owner);
    }

    @Transactional
    public void remove(Owner owner){
        verifyAllRules(owner);
        repo.delete(owner);
    }

    @Transactional
    public void removeById(Long id){
        verifyHasId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Owner removeByIdWithFeedback(Long id){
        verifyHasId(id);
        Owner feedback = repo.findByOwnerId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public Owner findById(Long id){
        verifyHasId(id);
        return repo.findByOwnerId(id);
    }

    @Transactional
    public List<Owner> find(Owner owner){
        verifyIsNull(owner);
        Example<Owner> example = generateFilter(owner);
        return repo.findAll(example);
    }

    @Transactional
    public List<Owner> findAll(){
        return repo.findAll();
    }

    @Transactional
    public List<Animal> findAllAnimalsByOwnerId(Long ownerId){
        verifyHasId(ownerId);
        Owner ownerFind = repo.findByOwnerId(ownerId);
        Hibernate.initialize(ownerFind.getAnimals());
        return ownerFind.getAnimals();
    }
}
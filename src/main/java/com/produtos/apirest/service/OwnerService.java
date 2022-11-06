package com.produtos.apirest.service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.repository.OwnerRepo;
import com.produtos.apirest.service.exceptions.BusinessRuleException;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepo repo;

    public OwnerService(OwnerRepo ownerRepo){
        this.repo = ownerRepo;
    }

    public static void isNotNull(Owner owner){
        if (owner == null)
            throw new NullPointerException("Owner must not be null!");
    }

    public static void hasId(Owner owner){
        if (owner == null || owner.getOwnerId() <= 0)
            throw new BusinessRuleException("The owner should have a id!");
    }

    public static void hasId(Long id){
        if (id <= 0)
            throw new BusinessRuleException("The owner should have a id!");
    }

    public static void hasName(Owner owner){
        if (owner.getName().equals(""))
            throw new BusinessRuleException("The owner should have a name!");
    }

    public static void hasCPF(Owner owner){
        if (owner.getCpf().equals(""))
            throw new BusinessRuleException("The owner should have a CPF!");
    }

    public static void verifyAllRules(Owner owner){
        isNotNull(owner);
        hasId(owner);
        hasCPF(owner);
        hasName(owner);
    }

    public static void IsNotNullHasCPFHasName(Owner owner){
        isNotNull(owner);
        hasCPF(owner);
        hasName(owner);
    }

    public static Example<Owner> generateExample(Owner owner){
        return Example.of(owner, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    }

    @Transactional
    public Owner save(Owner owner){
        IsNotNullHasCPFHasName(owner);
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
        hasId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Owner removeByIdWithFeedback(Long id){
        hasId(id);
        Owner feedback = repo.findByOwnerId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public Owner findById(Long id){
        hasId(id);
        return repo.findByOwnerId(id);
    }

    @Transactional
    public List<Owner> find(Owner owner){
        isNotNull(owner);
        Example<Owner> example = generateExample(owner);
        return repo.findAll(example);
    }

    @Transactional
    public List<Owner> findAll(){
        return repo.findAll();
    }

    @Transactional
    public List<Animal> findAllAnimalsByOwnerId(Long ownerId){
        hasId(ownerId);
        Owner ownerFind = repo.findByOwnerId(ownerId);
        Hibernate.initialize(ownerFind.getAnimals());
        return ownerFind.getAnimals();
    }
}
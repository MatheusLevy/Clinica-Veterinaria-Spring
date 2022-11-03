package com.produtos.apirest.service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.service.excecoes.BusinessRuleException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepo repo;

    public AnimalService(AnimalRepo animalRepo){
        this.repo = animalRepo;
    }

    public static void verifyAllRules(Animal animal){
        isNotNull(animal);
        hasName(animal);
        hasId(animal);
    }

    public static void isNotNull(Animal animal){
        if (animal == null)
            throw new NullPointerException("The animal must not be null!");
    }

    public static void isNotNullHasName(Animal animal){
        isNotNull(animal);
        hasName(animal);
    }

    public static void hasName(Animal animal){
        if(animal.getName().equals(""))
                throw new BusinessRuleException("The animal should have a name!");
    }

    public static void hasId(Animal animal){
        if (animal.getAnimalId() <= 0)
            throw new BusinessRuleException("The animal should have a id!");
    }

    public static void hasId(Long id){
        if (id <= 0)
            throw new BusinessRuleException("The animal should have a id!");
    }

    public static Example<Animal> generateExample(Animal animal){
        return Example.of(animal, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    }

    @Transactional
    public Animal save(Animal animal){
        isNotNullHasName(animal);
        return repo.save(animal);
    }

    @Transactional
    public Animal update(Animal animal){
        verifyAllRules(animal);
        return repo.save(animal);
    }

    @Transactional
    public Animal updateOwner(Animal source, Owner newOwner){
        OwnerService.verifyAllRules(newOwner);
        OwnerService.hasId(newOwner);
        verifyAllRules(source);
        source.setOwner(newOwner);
        return repo.save(source);
    }

    @Transactional
    public void remove(Animal animal){
        verifyAllRules(animal);
        repo.delete(animal);
    }

    @Transactional
    public void removeById(Long id){
        hasId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Animal removeByIdWithFeedback(Long id){
        hasId(id);
        Animal feedback = repo.findByAnimalId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public Animal findById(Long id){
        hasId(id);
        return repo.findByAnimalId(id);
    }

    @Transactional
    public List<Animal> find(Animal animal){
        isNotNull(animal);
        Example<Animal> example = generateExample(animal);
        return repo.findAll(example);
    }

    @Transactional
    public List<Animal> findAll(){
        return repo.findAll();
    }

    @Transactional
    public Owner findOwnerByAnimalId(Long animalId){
        hasId(animalId);
        Animal animalFind = repo.findByAnimalId(animalId);
        return animalFind.getOwner();
    }
}
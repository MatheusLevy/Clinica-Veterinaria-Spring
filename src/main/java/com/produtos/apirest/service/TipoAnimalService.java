package com.produtos.apirest.service;

import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.repository.AnimalTypeRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TipoAnimalService {

    private final AnimalTypeRepo repo;

    public TipoAnimalService(AnimalTypeRepo animalTypeRepo){
        this.repo = animalTypeRepo;
    }

    public static void isNotNull(AnimalType animalType){
        if (animalType == null)
            throw new NullPointerException("Animal Type must not be null!");
    }

    public static void hasName(AnimalType type){
        if (type.getName().equals(""))
            throw new RegraNegocioRunTime("The animal type should have a name!");
    }

    public static void hasId(AnimalType type){
        if(type.getAnimalTypeId() <= 0)
            throw new RegraNegocioRunTime("The animal type should have a id!");
    }

    public static void hasId(Long id){
        if(id <= 0)
            throw new RegraNegocioRunTime("The animal type should have a id!");
    }

    public static void verifyAllRules(AnimalType type){
        isNotNull(type);
        hasId(type);
        hasName(type);
    }

    public static void IsNotNullHasName(AnimalType type){
        isNotNull(type);
        hasName(type);
    }

    public static Example<AnimalType> generateExample(AnimalType animalType){
        return Example.of(animalType, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    }

    @Transactional
    public AnimalType save(AnimalType type){
        IsNotNullHasName(type);
        return repo.save(type);
    }

    @Transactional
    public AnimalType update(AnimalType type){
        verifyAllRules(type);
        return repo.save(type);
    }

    @Transactional
    public void remove(AnimalType type){
        verifyAllRules(type);
        repo.delete(type);
    }

    @Transactional
    public AnimalType removeByIdWithFeedback(Long id){
        hasId(id);
        AnimalType feedback = repo.findByAnimalTypeId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public void removeById(Long id){
        hasId(id);
        repo.deleteById(id);
    }

    @Transactional
    public AnimalType findById(Long id){
        hasId(id);
        return repo.findByAnimalTypeId(id);
    }

    @Transactional
    public List<AnimalType> find(AnimalType animalType){
        isNotNull(animalType);
        Example<AnimalType> example = generateExample(animalType);
        return repo.findAll(example);
    }

    @Transactional
    public List<AnimalType> findAll(){
        return repo.findAll();
    }
}
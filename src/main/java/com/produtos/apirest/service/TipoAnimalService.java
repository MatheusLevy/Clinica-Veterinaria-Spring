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

    public static void verifyIsNull(AnimalType animalType){
        if (animalType == null)
            throw new NullPointerException("Animal Type must not be null!");
    }

    public static void verifyHasName(AnimalType type){
        if (type.getName().equals(""))
            throw new RegraNegocioRunTime("The animal type should have a name!");
    }

    public static void verifyHasId(AnimalType type){
        if(type.getAnimalTypeId() <= 0)
            throw new RegraNegocioRunTime("The animal type should have a id!");
    }

    public static void verifyHasId(Long id){
        if(id <= 0)
            throw new RegraNegocioRunTime("The animal type should have a id!");
    }

    public static void verifyAllRules(AnimalType type){
        verifyIsNull(type);
        verifyHasId(type);
        verifyHasName(type);
    }

    public static void verifyIsNullHasName(AnimalType type){
        verifyIsNull(type);
        verifyHasName(type);
    }

    public static Example<AnimalType> generateFilter(AnimalType animalType){
        return Example.of(animalType, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    }

    @Transactional
    public AnimalType save(AnimalType type){
        verifyIsNullHasName(type);
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
        verifyHasId(id);
        AnimalType feedback = repo.findByAnimalTypeId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public void removeById(Long id){
        verifyHasId(id);
        repo.deleteById(id);
    }

    @Transactional
    public AnimalType findById(Long id){
        verifyHasId(id);
        return repo.findByAnimalTypeId(id);
    }

    @Transactional
    public List<AnimalType> find(AnimalType animalType){
        verifyIsNull(animalType);
        Example<AnimalType> example = generateFilter(animalType);
        return repo.findAll(example);
    }

    @Transactional
    public List<AnimalType> findAll(){
        return repo.findAll();
    }
}
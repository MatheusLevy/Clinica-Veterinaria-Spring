package com.produtos.apirest.service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.repository.ExpertiseRepo;
import com.produtos.apirest.service.excecoes.BusinessRuleException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ExpertiseService {

    private final ExpertiseRepo repo;

    public ExpertiseService(ExpertiseRepo expertiseRepo){
        this.repo = expertiseRepo;
    }

    public static void isNotNull(Expertise expertise){
        if (expertise == null)
            throw new NullPointerException("Expertise must not be null!");
    }

    public static void hasId(Expertise expertise){
        if (expertise.getExpertiseId() <= 0)
            throw new BusinessRuleException("The expertise should have a id!");
    }

    public static void hasId(Long id){
        if (id <= 0)
            throw new BusinessRuleException("The expertise should have a id!");
    }

    public static void hasName(Expertise expertise){
        if (expertise.getName().equals(""))
            throw new BusinessRuleException("The expertise should have a name!");
    }

    public static void hasArea(Expertise expertise){
        if (expertise.getArea() == null)
            throw new BusinessRuleException("The expertise should have a name!");
    }

    public static void verifyAllRules(Expertise expertise){
        isNotNull(expertise);
        hasId(expertise);
        hasName(expertise);
        hasArea(expertise);
    }

    public static void IsNotNullHasNameHasArea(Expertise expertise){
        isNotNull(expertise);
        hasName(expertise);
        hasArea(expertise);
    }

    public static Example<Expertise> generateExample(Expertise expertise){
        return Example.of(expertise, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    }

    @Transactional
    public Expertise save(Expertise expertise){
        IsNotNullHasNameHasArea(expertise);
        return repo.save(expertise);
    }

    @Transactional
    public Expertise update(Expertise expertise){
        verifyAllRules(expertise);
        return repo.save(expertise);
    }

    @Transactional
    public Expertise updateArea(Expertise destiny, Area newArea){
        verifyAllRules(destiny);
        AreaService.verifyAllRules(newArea);
        destiny.setArea(newArea);
        return repo.save(destiny);
    }

    @Transactional
    public void remove(Expertise expertise){
        verifyAllRules(expertise);
        repo.delete(expertise);
    }

    @Transactional
    public Expertise removeByIdWithFeedback(Long id){
        hasId(id);
        Expertise feedback = repo.findByExpertiseId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public void removeById(Long id){
        hasId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Expertise findById(Long id){
        hasId(id);
        return repo.findByExpertiseId(id);
    }

    @Transactional
    public List<Expertise> find(Expertise expertise){
        isNotNull(expertise);
        Example<Expertise> example = generateExample(expertise);
        return repo.findAll(example);
    }

    @Transactional
    public List<Expertise> findAll(){
        return repo.findAll();
    }
}
package com.produtos.apirest.service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.repository.ExpertiseRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EspecialidadeService {

    private final ExpertiseRepo repo;

    public EspecialidadeService(ExpertiseRepo expertiseRepo){
        this.repo = expertiseRepo;
    }

    public static void verifyIsNull(Expertise expertise){
        if (expertise == null)
            throw new NullPointerException("Expertise must not be null!");
    }

    public static void verifyHasId(Expertise expertise){
        if (expertise.getExpertiseId() <= 0)
            throw new RegraNegocioRunTime("The expertise should have a id!");
    }

    public static void verifyHasId(Long id){
        if (id <= 0)
            throw new RegraNegocioRunTime("The expertise should have a id!");
    }

    public static void verifyHasName(Expertise expertise){
        if (expertise.getName().equals(""))
            throw new RegraNegocioRunTime("The expertise should have a name!");
    }

    public static void verifyHasArea(Expertise expertise){
        if (expertise.getArea() == null)
            throw new RegraNegocioRunTime("The expertise should have a name!");
    }

    public static void verifyAllRules(Expertise expertise){
        verifyIsNull(expertise);
        verifyHasId(expertise);
        verifyHasName(expertise);
        verifyHasArea(expertise);
    }

    public static void verifyIsNullHasNameHasArea(Expertise expertise){
        verifyIsNull(expertise);
        verifyHasName(expertise);
        verifyHasArea(expertise);
    }

    public static Example<Expertise> generateFilter(Expertise expertise){
        return Example.of(expertise, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    }

    @Transactional
    public Expertise save(Expertise expertise){
        verifyIsNullHasNameHasArea(expertise);
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
        verifyHasId(id);
        Expertise feedback = repo.findByExpertiseId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public void removeById(Long id){
        verifyHasId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Expertise findById(Long id){
        verifyHasId(id);
        return repo.findByExpertiseId(id);
    }

    @Transactional
    public List<Expertise> find(Expertise expertise){
        verifyIsNull(expertise);
        Example<Expertise> example = generateFilter(expertise);
        return repo.findAll(example);
    }

    @Transactional
    public List<Expertise> findAll(){
        return repo.findAll();
    }
}
package com.produtos.apirest.service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AreaService {

    private final AreaRepo repo;

    public AreaService(AreaRepo areaRepo){
        this.repo = areaRepo;
    }

    public static void verifyAllRules(Area area){
        isNotNull(area);
        hasName(area);
        hasId(area);
    }

    public static void IsNotNullHasNome(Area area){
        isNotNull(area);
        hasName(area);
    }

    public static void isNotNull(Area area){
        if (area == null)
            throw new NullPointerException("The area must not be null!");
    }

    public static void hasName(Area area){
        if(area.getName().equals(""))
            throw new RegraNegocioRunTime("The area should have a name!");
    }

    public static void hasId(Area area){
        if (area.getAreaId() <= 0)
            throw new RegraNegocioRunTime("The area should have a id!");
    }

    public static void hasId(Long id){
        if (id <= 0)
            throw new RegraNegocioRunTime("The area should have a id!");
    }

    public static Example<Area> generateExample(Area area){
        return Example.of(area, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    }

    @Transactional
    public Area save(Area area){
        IsNotNullHasNome(area);
        return repo.save(area);
    }

    @Transactional
    public Area update(Area area){
        verifyAllRules(area);
        return repo.save(area);
    }

    @Transactional
    public void removeById(Long id){
        hasId(id);
        repo.deleteById(id);
    }

    @Transactional
    public void remove(Area area){
        verifyAllRules(area);
        repo.delete(area);
    }

    @Transactional
    public Area removeByIdWithFeedback(Long id){
        hasId(id);
        Area feedback = repo.findByAreaId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public Area findById(Long id){
        hasId(id);
        return repo.findByAreaId(id);
    }

    @Transactional
    public List<Area> find(Area area){
        isNotNull(area);
        Example<Area> example = generateExample(area);
        return repo.findAll(example);
    }

    @Transactional
    public List<Area> findAll(){
        return repo.findAll();
    }

    @Transactional
    public List<Expertise> findAllExpertiseByAreaId(Long areaId){
        hasId(areaId);
        Area areaFind = repo.findByAreaId(areaId);
        Hibernate.initialize(areaFind.getExpertises());
        return areaFind.getExpertises();
    }
}
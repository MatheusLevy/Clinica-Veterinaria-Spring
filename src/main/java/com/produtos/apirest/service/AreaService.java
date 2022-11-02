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
        verifyIsNull(area);
        verifyHasName(area);
        verifyHasId(area);
    }

    public static void verifyIsNullAndHasNome(Area area){
        verifyIsNull(area);
        verifyHasName(area);
    }

    public static void verifyIsNull(Area area){
        if (area == null)
            throw new NullPointerException("The area must not be null!");
    }

    public static void verifyHasName(Area area){
        if(area.getName().equals(""))
            throw new RegraNegocioRunTime("The area should have a name!");
    }

    public static void verifyHasId(Area area){
        if (area.getAreaId() <= 0)
            throw new RegraNegocioRunTime("The area should have a id!");
    }

    public static void verifyHasId(Long id){
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
        verifyIsNullAndHasNome(area);
        return repo.save(area);
    }

    @Transactional
    public Area update(Area area){
        verifyAllRules(area);
        return repo.save(area);
    }

    @Transactional
    public void removeById(Long id){
        verifyHasId(id);
        repo.deleteById(id);
    }

    @Transactional
    public void remove(Area area){
        verifyAllRules(area);
        repo.delete(area);
    }

    @Transactional
    public Area removeByIdWithFeedback(Long id){
        verifyHasId(id);
        Area feedback = repo.findByAreaId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public Area findById(Long id){
        verifyHasId(id);
        return repo.findByAreaId(id);
    }

    @Transactional
    public List<Area> find(Area area){
        verifyIsNull(area);
        Example<Area> example = generateExample(area);
        return repo.findAll(example);
    }

    @Transactional
    public List<Area> findAll(){
        return repo.findAll();
    }

    @Transactional
    public List<Expertise> findAllExpertiseByAreaId(Long areaId){
        verifyHasId(areaId);
        Area areaFind = repo.findByAreaId(areaId);
        Hibernate.initialize(areaFind.getExpertises());
        return areaFind.getExpertises();
    }
}
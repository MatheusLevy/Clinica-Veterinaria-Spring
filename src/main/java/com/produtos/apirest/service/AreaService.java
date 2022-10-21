package com.produtos.apirest.service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AreaService {
    @Autowired
    public AreaRepo repo;

    public static void verificaArea(Area area){
        if (area == null)
            throw new NullPointerException("A Area não pode ser Nula!");
        if(area.getNome() == null || area.getNome().equals(""))
            throw new RegraNegocioRunTime("A Area deve ter um nome!");
    }

    public static void verificaId(Area area){
        if (Long.valueOf(area.getAreaId()) == null || area == null)
            throw new RegraNegocioRunTime("A Area deve possuir um identificador!");
    }

    public static void verificaId(Long id){
        if (Long.valueOf(id) == null || id < 0)
            throw new RegraNegocioRunTime("A Area deve possuir um identificador!");
    }
    @Transactional
    public Area salvar(Area area){
        verificaArea(area);
        return repo.save(area);
    }

    @Transactional
    public Area atualizar(Area area){
        verificaArea(area);
        verificaId(area);
        return repo.save(area);
    }

    @Transactional
    public void removerPorId(Long id){
        verificaId(id);
        repo.deleteById(id);
    }

    @Transactional
    public void remover(Area area){
        verificaArea(area);
        verificaId(area);
        repo.delete(area);
    }

    @Transactional
    public Area removerFeedback(Long id){
        verificaId(id);
        Area areaFeedback = repo.findById(id).get();
        repo.delete(areaFeedback);
        return areaFeedback;
    }

    @Transactional
    public Area buscarPorId(Long id){
        verificaId(id);
        return repo.findById(id).get();
    }

    @Transactional
    public List<Area> buscar(Area filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<Area> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repo.findAll(example);
    }

    @Transactional
    public List<Area> buscarTodos(){
        return repo.findAll();
    }

    @Transactional
    public List<Especialidade> buscarTodasEspecialidades(Area area){
        verificaArea(area);
        verificaId(area);
        try {
            Area areaEncontrada = repo.findById(area.getAreaId()).get();
            Hibernate.initialize(areaEncontrada.getEspecialidades());
            verificaArea(areaEncontrada);
            verificaId(areaEncontrada);
            return areaEncontrada.getEspecialidades();
        } catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }
}
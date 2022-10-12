package com.produtos.apirest.service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    //TODO: Fazer captura de Exceção EmptyResultDataAccessException
    @Transactional
    public void remover(Long id){
        verificaId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Area removerFeedback(Long id){
        verificaId(id);
        Optional<Area> areaRemover = repo.findById(id);
        Area areaTemp = areaRemover.get();
        repo.delete(areaTemp);
        return areaTemp;
    }

    @Transactional
    public Area buscarAreaPorId(Long id){
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
            Optional<Area> areaTemp = repo.findById(area.getAreaId());
            Area areaBuscada = areaTemp.get();
            Hibernate.initialize(areaBuscada.getEspecialidades());
            verificaArea(areaBuscada);
            verificaId(areaBuscada);
            return areaBuscada.getEspecialidades();
        } catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }
}

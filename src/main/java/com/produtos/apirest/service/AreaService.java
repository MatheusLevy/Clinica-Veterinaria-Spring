package com.produtos.apirest.service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.service.excecoes.NotIdentifiableObject;
import com.produtos.apirest.service.excecoes.NotNamedArea;
import com.produtos.apirest.service.excecoes.NullAreaException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new NullAreaException();
        if(area.getNome() == null || area.getNome().equals(""))
            throw new NotNamedArea();
    }

    public static void verificaId(Area area){
        if (Long.valueOf(area.getAreaId()) == null || area == null)
            throw new NotIdentifiableObject(area);
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
    public void remover(Area area){
        verificaArea(area);
        verificaId(area);
        repo.delete(area);
    }

    @Transactional
    public Area removerFeedback(Area area){
        verificaArea(area);
        verificaId(area);
        Optional<Area> areaRemover = repo.findById(area.getAreaId());
        Area areaTemp = areaRemover.get();
        repo.delete(areaTemp);
        return areaTemp;
    }

    @Transactional
    public Area buscarAreaPorId(Area area){
        verificaId(area);
        return repo.findById(area.getAreaId()).get();
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

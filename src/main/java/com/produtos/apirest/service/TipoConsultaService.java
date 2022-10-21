package com.produtos.apirest.service;

import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.repository.TipoConsultaRepo;
import com.produtos.apirest.service.excecoes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TipoConsultaService {

    @Autowired
    public TipoConsultaRepo repo;

    public static void verificaTipoConsulta(TipoConsulta tipo){
        if (tipo == null)
            throw new NullPointerException("Tipo de Consulta não pode ser Nulo!");
        if (tipo.getNome() == null || tipo.getNome().equals(""))
            throw new RegraNegocioRunTime("Tipo de Consulta deve ter um nome!");
    }

    public static void verificaId(TipoConsulta tipo){
        if(tipo == null || Long.valueOf(tipo.getTipoConsultaId()) == null)
            throw new RegraNegocioRunTime("Tipo de Consulta deve ter um identificador!");
    }

    public static void verificaId(Long id){
        if(Long.valueOf(id) == null)
            throw new RegraNegocioRunTime("Tipo de Consulta deve ter um identificador!");
    }

    @Transactional
    public TipoConsulta salvar(TipoConsulta tipo){
        verificaTipoConsulta(tipo);
        return repo.save(tipo);
    }

    @Transactional
    public TipoConsulta atualizar(TipoConsulta tipo){
        verificaTipoConsulta(tipo);
        verificaId(tipo);
        return repo.save(tipo);
    }

    @Transactional
    public void removerPorId(Long id){
        verificaId(id);
        repo.deleteById(id);
    }

    @Transactional
    public TipoConsulta removerComFeedback(Long id){
        verificaId(id);
        TipoConsulta tipoConsultaFeedback = repo.findById(id).get();
        repo.delete(tipoConsultaFeedback);
        return tipoConsultaFeedback;
    }

    @Transactional
    public TipoConsulta buscarPorId(Long id){
        verificaId(id);
        return repo.findById(id).get();
    }

    @Transactional
    public List<TipoConsulta> buscar(TipoConsulta filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<TipoConsulta> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repo.findAll(example);
    }

    @Transactional
    public List<TipoConsulta> buscarTodos(){
        return repo.findAll();
    }
}
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
import java.util.Optional;

//TODO: **Alterar Nome da Classe para CamelCase
@Service
public class TipoConsultaService {

    @Autowired
    public TipoConsultaRepo repo;

    public static void verificaTipo_consulta(TipoConsulta tipo){
        if (tipo == null)
            throw new NullPointerException("Tipo de Consulta não pode ser Nulo!");
        if (tipo.getNome() == null || tipo.getNome().equals(""))
            throw new RegraNegocioRunTime("Tipo de Consulta deve ter um nome!");
    }

    public static void verificaId(TipoConsulta tipo){
        if(tipo == null || Long.valueOf(tipo.getTipoConsultaId()) == null)
            throw new RegraNegocioRunTime("Tipo de Consulta deve ter um identificador!");
    }

    @Transactional
    public TipoConsulta salvar(TipoConsulta tipo){
        verificaTipo_consulta(tipo);
        return repo.save(tipo);
    }

    @Transactional
    public TipoConsulta atualizar(TipoConsulta tipo){
        verificaTipo_consulta(tipo);
        verificaId(tipo);
        return repo.save(tipo);
    }

    @Transactional
    public void remover(TipoConsulta tipo){
        verificaTipo_consulta(tipo);
        verificaId(tipo);
        repo.delete(tipo);
    }

    @Transactional
    public TipoConsulta removerFeedback(TipoConsulta tipo){
        verificaTipo_consulta(tipo);
        verificaId(tipo);
        Optional<TipoConsulta> tipoRemover = repo.findById(tipo.getTipoConsultaId());
        TipoConsulta tipoTemp = tipoRemover.get();
        repo.delete(tipoTemp);
        return tipoTemp;
    }

    @Transactional
    public TipoConsulta buscarTipo_consultaPorId(TipoConsulta tipo){
        verificaId(tipo);
        return repo.findById(tipo.getTipoConsultaId()).get();
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

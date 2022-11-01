package com.produtos.apirest.service;

import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.repository.TipoConsultaRepo;
import com.produtos.apirest.service.excecoes.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TipoConsultaService {

    private final TipoConsultaRepo repo;

    public TipoConsultaService(TipoConsultaRepo tipoConsultaRepo){
        this.repo = tipoConsultaRepo;
    }

    public static void verificaTipoConsulta(TipoConsulta tipo){
        if (tipo == null)
            throw new NullPointerException("Tipo de Consulta não pode ser Nulo!");
        if (tipo.getName().equals(""))
            throw new RegraNegocioRunTime("Tipo de Consulta deve ter um nome!");
    }

    public static void verificaId(TipoConsulta tipo){
        if(tipo == null || tipo.getAppointmentTypeId() <= 0)
            throw new RegraNegocioRunTime("Tipo de Consulta deve ter um identificador!");
    }

    public static void verificaId(Long id){
        if(id <= 0)
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
        Optional<TipoConsulta> tipoConsultaEncontradas = repo.findById(id);
        if (tipoConsultaEncontradas.isPresent()) {
            TipoConsulta tipoConsultaFeedback = tipoConsultaEncontradas.get();
            repo.delete(tipoConsultaFeedback);
            return tipoConsultaFeedback;
        }
        return null;
    }

    @Transactional
    public TipoConsulta buscarPorId(Long id){
        verificaId(id);
        Optional<TipoConsulta> tipoConsultaEncontradas = repo.findById(id);
        return tipoConsultaEncontradas.orElse(null);
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
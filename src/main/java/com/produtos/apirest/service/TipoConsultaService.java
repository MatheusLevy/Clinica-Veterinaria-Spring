package com.produtos.apirest.service;

import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.repository.AppointmentTypeRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TipoConsultaService {

    private final AppointmentTypeRepo repo;

    public TipoConsultaService(AppointmentTypeRepo appointmentTypeRepo){
        this.repo = appointmentTypeRepo;
    }

    public static void verificaTipoConsulta(AppointmentType tipo){
        if (tipo == null)
            throw new NullPointerException("Tipo de Consulta não pode ser Nulo!");
        if (tipo.getName().equals(""))
            throw new RegraNegocioRunTime("Tipo de Consulta deve ter um nome!");
    }

    public static void verificaId(AppointmentType tipo){
        if(tipo == null || tipo.getAppointmentTypeId() <= 0)
            throw new RegraNegocioRunTime("Tipo de Consulta deve ter um identificador!");
    }

    public static void verificaId(Long id){
        if(id <= 0)
            throw new RegraNegocioRunTime("Tipo de Consulta deve ter um identificador!");
    }

    @Transactional
    public AppointmentType salvar(AppointmentType tipo){
        verificaTipoConsulta(tipo);
        return repo.save(tipo);
    }

    @Transactional
    public AppointmentType atualizar(AppointmentType tipo){
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
    public AppointmentType removerComFeedback(Long id){
        verificaId(id);
        Optional<AppointmentType> tipoConsultaEncontradas = repo.findById(id);
        if (tipoConsultaEncontradas.isPresent()) {
            AppointmentType tipoConsultaFeedback = tipoConsultaEncontradas.get();
            repo.delete(tipoConsultaFeedback);
            return tipoConsultaFeedback;
        }
        return null;
    }

    @Transactional
    public AppointmentType buscarPorId(Long id){
        verificaId(id);
        Optional<AppointmentType> tipoConsultaEncontradas = repo.findById(id);
        return tipoConsultaEncontradas.orElse(null);
    }

    @Transactional
    public List<AppointmentType> buscar(AppointmentType filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<AppointmentType> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repo.findAll(example);
    }

    @Transactional
    public List<AppointmentType> buscarTodos(){
        return repo.findAll();
    }
}
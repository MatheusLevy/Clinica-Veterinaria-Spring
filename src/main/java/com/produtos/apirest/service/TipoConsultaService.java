package com.produtos.apirest.service;

import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.repository.AppointmentTypeRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TipoConsultaService {

    private final AppointmentTypeRepo repo;

    public TipoConsultaService(AppointmentTypeRepo appointmentTypeRepo){
        this.repo = appointmentTypeRepo;
    }

    public static void verifyIsNull(AppointmentType appointmentType){
        if (appointmentType == null)
            throw new NullPointerException("Appointment Type must not be null!");
    }

    public static void verifyHasName(AppointmentType appointmentType){
        if (appointmentType.getName().equals(""))
            throw new RegraNegocioRunTime("Tipo de Consulta deve ter um nome!");
    }

    public static void verifyId(AppointmentType appointmentType){
        if(appointmentType.getAppointmentTypeId() <= 0)
            throw new RegraNegocioRunTime("The appointment type should have a id!");
    }

    public static void verifyId(Long id){
        if(id <= 0)
            throw new RegraNegocioRunTime("The appointment type should have a id!");
    }

    public static void verifyAllRules(AppointmentType appointmentType){
        verifyIsNull(appointmentType);
        verifyId(appointmentType);
        verifyHasName(appointmentType);
    }

    public static void verifyIsNullHasName(AppointmentType appointmentType){
        verifyIsNull(appointmentType);
        verifyHasName(appointmentType);
    }

    public static Example<AppointmentType> generateFilter(AppointmentType appointmentType){
        return Example.of(appointmentType, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    }

    @Transactional
    public AppointmentType save(AppointmentType type){
        verifyIsNullHasName(type);
        return repo.save(type);
    }

    @Transactional
    public AppointmentType update(AppointmentType type){
        verifyAllRules(type);
        return repo.save(type);
    }

    @Transactional
    public void removeById(Long id){
        verifyId(id);
        repo.deleteById(id);
    }

    @Transactional
    public AppointmentType removeByIdWithFeedback(Long id){
        verifyId(id);
        AppointmentType feedback = repo.findByAppointmentTypeId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public AppointmentType findById(Long id){
        verifyId(id);
        return repo.findByAppointmentTypeId(id);
    }

    @Transactional
    public List<AppointmentType> find(AppointmentType appointmentType){
        verifyIsNull(appointmentType);
        Example<AppointmentType> example = generateFilter(appointmentType);
        return repo.findAll(example);
    }

    @Transactional
    public List<AppointmentType> findAll(){
        return repo.findAll();
    }
}
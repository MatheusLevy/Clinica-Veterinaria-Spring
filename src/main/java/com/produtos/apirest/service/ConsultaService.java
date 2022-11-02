package com.produtos.apirest.service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Appointment;
import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.repository.AppointmentRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class ConsultaService {

    private final AppointmentRepo repo;

    public ConsultaService(AppointmentRepo consultaRepo){
        this.repo = consultaRepo;
    }

    public static void verifyIsNull(Appointment appointment){
        if(appointment == null)
            throw new NullPointerException("The appointment must not be null!");
    }

    public static void verifyHasAnimal(Appointment appointment){
        if(appointment.getAnimal() == null)
            throw new RegraNegocioRunTime("The appointment should hava a animal!");
    }

    public static void verifyHasType(Appointment appointment){
        if(appointment.getAppointmentType() == null)
            throw new RegraNegocioRunTime("The appointment should have a type!");
    }

    public static void verifyHasVeterinary(Appointment appointment){
        if (appointment.getVeterinary() == null)
            throw new RegraNegocioRunTime("The appointment should have a veterinary!");
    }

    public static void verifyHasDate(Appointment appointment){
        if(appointment.getDate() == null)
            throw new RegraNegocioRunTime("The appointment should have a date!");
    }

    public static void verifyHasId(Appointment appointment){
        if (appointment == null || appointment.getAppointmentId() <= 0)
            throw new RegraNegocioRunTime("The appointment should have a id!");
    }

    public static void verifyHasId(Long id){
        if (id <= 0)
            throw new RegraNegocioRunTime("The appointment should have a id!");
    }

    public static void verifyAllRules(Appointment appointment){
        verifyIsNull(appointment);
        verifyHasId(appointment);
        verifyHasAnimal(appointment);
        verifyHasType(appointment);
        verifyHasDate(appointment);
        verifyHasVeterinary(appointment);
    }

    public static void verifyIsNullHasAnimalHasTypeHasDateHasVeterinary(Appointment appointment){
        verifyIsNull(appointment);
        verifyHasAnimal(appointment);
        verifyHasType(appointment);
        verifyHasDate(appointment);
        verifyHasVeterinary(appointment);
    }

    @Transactional
    public Appointment save(Appointment appointment){
        verifyIsNullHasAnimalHasTypeHasDateHasVeterinary(appointment);
        return repo.save(appointment);
    }

    @Transactional
    public Appointment update(Appointment appointment){
        verifyAllRules(appointment);
        return repo.save(appointment);
    }

    @Transactional
    public Appointment updateAppointment(Appointment destiny, Veterinary newVeterinary){
        VeterinarioService.verifyAllRules(newVeterinary);
        VeterinarioService.verifyHasId(newVeterinary);
        verifyAllRules(destiny);
        destiny.setVeterinary(newVeterinary);
        return repo.save(destiny);
    }

    @Transactional
    public Appointment updateAnimal(Appointment destiny, Animal newAnimal){
        AnimalService.verifyAllRules(newAnimal);
        verifyAllRules(destiny);
        destiny.setAnimal(newAnimal);
        return repo.save(destiny);
    }

    @Transactional
    public Appointment updateAppointmentType(Appointment destiny, AppointmentType newType){
        TipoConsultaService.verifyAllRules(newType);
        TipoConsultaService.verifyId(newType);
        verifyAllRules(destiny);
        destiny.setAppointmentType(newType);
        return repo.save(destiny);
    }

    @Transactional
    public void remove(Appointment appointment){
        verifyAllRules(appointment);
        repo.delete(appointment);
    }

    @Transactional
    public void removeById(Long id){
        verifyHasId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Appointment removeByIdWithFeedback(Long id){
        verifyHasId(id);
        Appointment feedback = repo.findByAppointmentId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public Appointment findById(Long id){
        verifyHasId(id);
        return repo.findByAppointmentId(id);
    }

    @Transactional
    public List<Appointment> findAll(){
        return repo.findAll();
    }
}
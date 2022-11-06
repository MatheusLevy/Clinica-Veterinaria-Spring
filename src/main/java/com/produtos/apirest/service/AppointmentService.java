package com.produtos.apirest.service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Appointment;
import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.repository.AppointmentRepo;
import com.produtos.apirest.service.exceptions.BusinessRuleException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class AppointmentService {

    private final AppointmentRepo repo;

    public AppointmentService(AppointmentRepo consultaRepo){
        this.repo = consultaRepo;
    }

    public static void isNotNull(Appointment appointment){
        if(appointment == null)
            throw new NullPointerException("The appointment must not be null!");
    }

    public static void hasAnimal(Appointment appointment){
        if(appointment.getAnimal() == null)
            throw new BusinessRuleException("The appointment should hava a animal!");
    }

    public static void hasType(Appointment appointment){
        if(appointment.getAppointmentType() == null)
            throw new BusinessRuleException("The appointment should have a type!");
    }

    public static void hasVeterinary(Appointment appointment){
        if (appointment.getVeterinary() == null)
            throw new BusinessRuleException("The appointment should have a veterinary!");
    }

    public static void hasDate(Appointment appointment){
        if(appointment.getDate() == null)
            throw new BusinessRuleException("The appointment should have a date!");
    }

    public static void hasId(Appointment appointment){
        if (appointment == null || appointment.getAppointmentId() <= 0)
            throw new BusinessRuleException("The appointment should have a id!");
    }

    public static void hasId(Long id){
        if (id <= 0)
            throw new BusinessRuleException("The appointment should have a id!");
    }

    public static void verifyAllRules(Appointment appointment){
        isNotNull(appointment);
        hasId(appointment);
        hasAnimal(appointment);
        hasType(appointment);
        hasDate(appointment);
        hasVeterinary(appointment);
    }

    public static void IsNotNullHasAnimalHasTypeHasDateHasVeterinary(Appointment appointment){
        isNotNull(appointment);
        hasAnimal(appointment);
        hasType(appointment);
        hasDate(appointment);
        hasVeterinary(appointment);
    }

    @Transactional
    public Appointment save(Appointment appointment){
        IsNotNullHasAnimalHasTypeHasDateHasVeterinary(appointment);
        return repo.save(appointment);
    }

    @Transactional
    public Appointment update(Appointment appointment){
        verifyAllRules(appointment);
        return repo.save(appointment);
    }

    @Transactional
    public Appointment updateVeterinary(Appointment destiny, Veterinary newVeterinary){
        VeterinaryService.verifyAllRules(newVeterinary);
        VeterinaryService.hasId(newVeterinary);
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
        AppointmentTypeService.verifyAllRules(newType);
        AppointmentTypeService.hasId(newType);
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
        hasId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Appointment removeByIdWithFeedback(Long id){
        hasId(id);
        Appointment feedback = repo.findByAppointmentId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public Appointment findById(Long id){
        hasId(id);
        return repo.findByAppointmentId(id);
    }

    @Transactional
    public List<Appointment> findAll(){
        return repo.findAll();
    }
}
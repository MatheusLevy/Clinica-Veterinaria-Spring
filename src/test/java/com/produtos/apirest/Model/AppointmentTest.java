package com.produtos.apirest.Model;

import com.produtos.apirest.models.*;
import com.produtos.apirest.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;

import static com.produtos.apirest.Model.AnimalTest.generateAnimal;
import static com.produtos.apirest.Model.AnimalTest.rollbackAnimal;
import static com.produtos.apirest.Model.AppointmentTypeTest.generateAppointmentType;
import static com.produtos.apirest.Model.AppointmentTypeTest.rollbackAppointmentType;
import static com.produtos.apirest.Model.VeterinaryTest.generateVeterinary;
import static com.produtos.apirest.Model.VeterinaryTest.rollbackVeterinary;

@SpringBootTest
public class AppointmentTest {
    @Autowired
    public AppointmentRepo appointmentRepo;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public OwnerRepo ownerRepo;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public ExpertiseRepo expertiseRepo;

    @Autowired
    public AppointmentTypeRepo appointmentTypeRepo;

    @Autowired
    public AnimalTypeRepo animalTypeRepo;

    @Autowired
    public VeterinaryRepo veterinaryRepo;

    protected Appointment generateAppointment(Boolean initFields){
        Appointment appointment = Appointment.builder()
                .description("description")
                .date(LocalDate.now())
                .appointmentType(generateAppointmentType())
                .veterinary(generateVeterinary(true, expertiseRepo, areaRepo))
                .animal(generateAnimal(animalTypeRepo, ownerRepo))
                .build();
        if(initFields){
            appointment.setAppointmentType(appointmentTypeRepo.save(appointment.getAppointmentType()));
            appointment.setVeterinary(veterinaryRepo.save(appointment.getVeterinary()));
            appointment.setAnimal(animalRepo.save(appointment.getAnimal()));
        }
        return appointment;
    }

    private void rollback(Appointment appointment, Boolean skipAppointment){
        if (!skipAppointment)
            appointmentRepo.delete(appointment);
        veterinaryRepo.delete(appointment.getVeterinary());
        expertiseRepo.delete(appointment.getVeterinary().getExpertise());
        areaRepo.delete(appointment.getVeterinary().getExpertise().getArea());
        animalRepo.delete(appointment.getAnimal());
        animalTypeRepo.delete(appointment.getAnimal().getAnimalType());
        ownerRepo.delete(appointment.getAnimal().getOwner());
        appointmentTypeRepo.delete(appointment.getAppointmentType());
    }

    protected static void rollbackAppointment(Appointment appointment, AppointmentRepo appointmentRepo,
                                              VeterinaryRepo veterinaryRepo, ExpertiseRepo expertiseRepo,
                                              AreaRepo areaRepo, AnimalRepo animalRepo,
                                              AnimalTypeRepo animalTypeRepo, OwnerRepo ownerRepo, AppointmentTypeRepo appointmentTypeRepo,
                                              Boolean skipAppointment){
        if (!skipAppointment)
            appointmentRepo.delete(appointment);
        veterinaryRepo.delete(appointment.getVeterinary());
        expertiseRepo.delete(appointment.getVeterinary().getExpertise());
        areaRepo.delete(appointment.getVeterinary().getExpertise().getArea());
        animalRepo.delete(appointment.getAnimal());
        animalTypeRepo.delete(appointment.getAnimal().getAnimalType());
        ownerRepo.delete(appointment.getAnimal().getOwner());
        appointmentTypeRepo.delete(appointment.getAppointmentType());
    }

    @Test
    public void save(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        Assertions.assertNotNull(appointmentSaved);
        rollback(appointmentSaved, false);
    }

    @Test
    public void update(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        appointmentSaved.setDescription("New Description");
        Appointment appointmentUpdated = appointmentRepo.save(appointmentSaved);
        Assertions.assertNotNull(appointmentUpdated);
        Assertions.assertEquals(appointmentSaved.getAppointmentId(), appointmentUpdated.getAppointmentId());
        Assertions.assertEquals(appointmentUpdated.getDescription(), "New Description");
        rollback(appointmentSaved, false);
    }

    @Test
    public void updateAppointmentType(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        AppointmentType appointmentTypeOld = appointmentSaved.getAppointmentType();
        appointmentSaved.setAppointmentType(appointmentTypeRepo.save(generateAppointmentType()));
        Appointment appointmentUpdated = appointmentRepo.save(appointmentSaved);
        Assertions.assertNotNull(appointmentUpdated);
        Assertions.assertEquals(appointmentSaved.getAppointmentId(), appointmentUpdated.getAppointmentId());
        Assertions.assertEquals(appointmentUpdated.getAppointmentType().getAppointmentTypeId(), appointmentSaved.getAppointmentType().getAppointmentTypeId());
        rollback(appointmentSaved, false);
        rollbackAppointmentType(appointmentTypeOld, appointmentTypeRepo);
    }

    @Test
    public void updateAnimal(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        Animal animalOld = appointmentSaved.getAnimal();
        appointmentSaved.setAnimal(animalRepo.save(generateAnimal(animalTypeRepo, ownerRepo)));
        Appointment appointmentUpdated = appointmentRepo.save(appointmentSaved);
        Assertions.assertNotNull(appointmentUpdated);
        Assertions.assertEquals(appointmentSaved.getAppointmentId(), appointmentUpdated.getAppointmentId());
        Assertions.assertEquals(appointmentUpdated.getAnimal().getAnimalId(), appointmentSaved.getAnimal().getAnimalId());
        rollback(appointmentSaved, false);
        rollbackAnimal(animalOld, animalRepo, animalTypeRepo, ownerRepo);
    }

    @Test
    public void updateVeterinary(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        Veterinary veterinaryOld = appointmentSaved.getVeterinary();
        appointmentSaved.setVeterinary(veterinaryRepo.save(generateVeterinary(true, expertiseRepo, areaRepo)));
        Appointment appointmentUpdated = appointmentRepo.save(appointmentSaved);
        Assertions.assertNotNull(appointmentUpdated);
        Assertions.assertEquals(appointmentSaved.getAppointmentId(), appointmentUpdated.getAppointmentId());
        Assertions.assertEquals(appointmentUpdated.getVeterinary().getVeterinaryId(), appointmentSaved.getVeterinary().getVeterinaryId());
        rollback(appointmentSaved, false);
        rollbackVeterinary(veterinaryOld, veterinaryRepo, areaRepo, expertiseRepo);
    }

    @Test
    public void removeById(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        Long id = appointmentSaved.getAppointmentId();
        appointmentRepo.deleteById(id);
        Assertions.assertNull(appointmentRepo.findByAppointmentId(id));
        rollback(appointmentSaved, true);
    }

    @Test
    public void findById(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        Long id = appointmentSaved.getAppointmentId();
        Assertions.assertNotNull(appointmentRepo.findByAppointmentId(id));
        rollback(appointmentSaved, false);
    }
}
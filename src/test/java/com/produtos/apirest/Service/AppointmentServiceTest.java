package com.produtos.apirest.Service;

import com.produtos.apirest.models.*;
import com.produtos.apirest.repository.*;
import com.produtos.apirest.service.AppointmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static com.produtos.apirest.Service.AnimalServiceTest.generateAnimal;
import static com.produtos.apirest.Service.AnimalServiceTest.rollbackAnimal;
import static com.produtos.apirest.Service.AppointmentTypeServiceTest.generateAppointmentType;
import static com.produtos.apirest.Service.AppointmentTypeServiceTest.rollbackAppointmentType;
import static com.produtos.apirest.Service.VeterinaryServiceTest.generateVeterinary;
import static com.produtos.apirest.Service.VeterinaryServiceTest.rollbackVeterinary;


@SpringBootTest
public class AppointmentServiceTest {

    @Autowired
    public AppointmentService appointmentService;

    @Autowired
    public AppointmentRepo appointmentRepo;

    @Autowired
    public OwnerRepo ownerRepo;

    @Autowired
    public AnimalTypeRepo animalTypeRepo;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public ExpertiseRepo expertiseRepo;

    @Autowired
    public VeterinaryRepo veterinaryRepo;

    @Autowired
    public AppointmentTypeRepo appointmentTypeRepo;

    private Appointment generateAppointment(Boolean initFields){
        Appointment appointment = Appointment.builder()
                .description("description")
                .date(LocalDate.now())
                .appointmentType(generateAppointmentType())
                .veterinary(generateVeterinary(false, areaRepo, expertiseRepo))
                .animal(generateAnimal(animalTypeRepo, ownerRepo, false))
                .build();
        if (initFields){
            appointment.setAppointmentType(appointmentTypeRepo.save(generateAppointmentType()));
            appointment.setVeterinary(veterinaryRepo.save(generateVeterinary(true, areaRepo, expertiseRepo)));
            appointment.setAnimal(animalRepo.save(generateAnimal(animalTypeRepo, ownerRepo, true)));
        }
        return appointment;
    }

    protected static Appointment generateAppointment(AreaRepo areaRepo, ExpertiseRepo expertiseRepo,
                                                     AnimalTypeRepo animalTypeRepo, OwnerRepo ownerRepo,
                                                     AppointmentTypeRepo appointmentTypeRepo, VeterinaryRepo veterinaryRepo,
                                                     AnimalRepo animalRepo, Boolean initializeFields){
        Appointment appointment = Appointment.builder()
                .description("description")
                .date(LocalDate.now())
                .appointmentType(generateAppointmentType())
                .veterinary(generateVeterinary(false, areaRepo, expertiseRepo))
                .animal(generateAnimal(animalTypeRepo, ownerRepo, false))
                .build();
        if (initializeFields){
            appointment.setAppointmentType(appointmentTypeRepo.save(appointment.getAppointmentType()));
            appointment.setVeterinary(veterinaryRepo.save(appointment.getVeterinary()));
            appointment.setAnimal(animalRepo.save(appointment.getAnimal()));
        }
        return appointment;
    }

    private void rollback(Appointment appointment, Boolean skipAppointment){
        if (!skipAppointment)
            appointmentRepo.delete(appointment);
        animalRepo.delete(appointment.getAnimal());
        veterinaryRepo.deleteById(appointment.getVeterinary().getVeterinaryId());
        animalTypeRepo.delete(appointment.getAnimal().getAnimalType());
        appointmentTypeRepo.delete(appointment.getAppointmentType());
        expertiseRepo.delete(appointment.getVeterinary().getExpertise());
        areaRepo.delete(appointment.getVeterinary().getExpertise().getArea());
        ownerRepo.delete(appointment.getAnimal().getOwner());
    }

    protected static void rollbackAppointment(Appointment appointment, AppointmentRepo appointmentRepo,
                                              AnimalRepo animalRepo, VeterinaryRepo veterinaryRepo,
                                              AnimalTypeRepo animalTypeRepo, AppointmentTypeRepo appointmentTypeRepo,
                                              ExpertiseRepo expertiseRepo, AreaRepo areaRepo,
                                              OwnerRepo ownerRepo, Boolean skipAppointment){
        if (!skipAppointment)
            appointmentRepo.delete(appointment);
        animalRepo.delete(appointment.getAnimal());
        veterinaryRepo.deleteById(appointment.getVeterinary().getVeterinaryId());
        animalTypeRepo.delete(appointment.getAnimal().getAnimalType());
        appointmentTypeRepo.delete(appointment.getAppointmentType());
        expertiseRepo.delete(appointment.getVeterinary().getExpertise());
        areaRepo.delete(appointment.getVeterinary().getExpertise().getArea());
        ownerRepo.delete(appointment.getAnimal().getOwner());
    }

    @Test
    public void save(){
        Appointment appointmentSaved = appointmentService.save(generateAppointment(true));
        Assertions.assertNotNull(appointmentSaved);
        rollback(appointmentSaved, false);
    }

    @Test
    public void update(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        appointmentSaved.setDescription("New Desc");
        Appointment appointmentUpdated = appointmentService.update(appointmentSaved);
        Assertions.assertNotNull(appointmentUpdated);
        Assertions.assertEquals(appointmentSaved.getAppointmentId(), appointmentUpdated.getAppointmentId());
        Assertions.assertEquals(appointmentUpdated.getDescription(), "New Desc");
        rollback(appointmentSaved, false);
    }

    @Test
    public void remove(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        Long id = appointmentSaved.getAppointmentId();
        appointmentService.remove(appointmentSaved);
        Assertions.assertNull(appointmentRepo.findByAppointmentId(id));
        rollback(appointmentSaved, true);
    }

    @Test
    public void removeById(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        Long id = appointmentSaved.getAppointmentId();
        appointmentService.removeById(appointmentSaved.getAppointmentId());
        Assertions.assertNull(appointmentRepo.findByAppointmentId(id));
        rollback(appointmentSaved, true);
    }

    @Test
    public void removeByIdWithFeedback(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        Appointment feedback = appointmentService.removeByIdWithFeedback(appointmentSaved.getAppointmentId());
        Assertions.assertNotNull(feedback);
        Assertions.assertEquals(feedback.getAppointmentId(), appointmentSaved.getAppointmentId());
        rollback(appointmentSaved, true);
    }

    @Test
    public void updateVeterinary(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        Veterinary veterinaryOld = appointmentSaved.getVeterinary();
        Veterinary veterinaryNew = veterinaryRepo.save(generateVeterinary(true, areaRepo, expertiseRepo));
        Appointment appointmentUpdated = appointmentService.updateVeterinary(appointmentSaved, veterinaryNew);
        Assertions.assertNotNull(appointmentUpdated);
        Assertions.assertEquals(appointmentUpdated.getVeterinary().getVeterinaryId(), veterinaryNew.getVeterinaryId());
        rollback(appointmentUpdated, false);
        rollbackVeterinary(veterinaryOld, veterinaryRepo, areaRepo, expertiseRepo, false);
    }

    @Test
    public void updateAnimal(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        Animal animalOld = appointmentSaved.getAnimal();
        Animal animalNew = animalRepo.save(generateAnimal(animalTypeRepo, ownerRepo, true));
        Appointment appointmentUpdated = appointmentService.updateAnimal(appointmentSaved, animalNew);
        Assertions.assertNotNull(appointmentUpdated);
        Assertions.assertEquals(appointmentUpdated.getAnimal().getAnimalId(), animalNew.getAnimalId());
        rollback(appointmentUpdated, false);
        rollbackAnimal(animalOld, animalRepo, ownerRepo, animalTypeRepo);
    }

    @Test
    public void updateAppointmentType(){
        Appointment appointmentSaved = appointmentRepo.save(generateAppointment(true));
        AppointmentType typeOld = appointmentSaved.getAppointmentType();
        AppointmentType typeNew = appointmentTypeRepo.save(generateAppointmentType());
        Appointment appointmentUpdated = appointmentService.updateAppointmentType(appointmentSaved, typeNew);
        Assertions.assertNotNull(appointmentUpdated);
        Assertions.assertEquals(appointmentUpdated.getAppointmentType().getAppointmentTypeId(), typeNew.getAppointmentTypeId());
        rollback(appointmentSaved, false);
        rollbackAppointmentType(typeOld, appointmentTypeRepo);
    }

    @Test
    public void findById(){
        Appointment appointmentSaved = appointmentService.save(generateAppointment(true));
        Appointment appointmentFind = appointmentService.findById(appointmentSaved.getAppointmentId());
        Assertions.assertNotNull(appointmentFind);
        Assertions.assertEquals(appointmentFind.getAppointmentId(), appointmentSaved.getAppointmentId());
        rollback(appointmentSaved, false);
    }

    @Test
    public void findAll(){
        Appointment appointmentSaved = appointmentService.save(generateAppointment(true));
        List<Appointment> appointments = appointmentService.findAll();
        Assertions.assertNotNull(appointments);
        Assertions.assertFalse(appointments.isEmpty());
        rollback(appointmentSaved, false);
    }
}
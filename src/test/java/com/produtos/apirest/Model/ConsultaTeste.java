package com.produtos.apirest.Model;

import com.produtos.apirest.models.*;
import com.produtos.apirest.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;

import static com.produtos.apirest.Model.AnimalTeste.generateAnimal;
import static com.produtos.apirest.Model.AnimalTeste.rollbackAnimal;
import static com.produtos.apirest.Model.TipoConsultaTeste.generateTipoConsulta;
import static com.produtos.apirest.Model.TipoConsultaTeste.rollbackTipoConsulta;
import static com.produtos.apirest.Model.VeterinarioTeste.generateVeterinario;
import static com.produtos.apirest.Model.VeterinarioTeste.rollbackVeterinario;

@SpringBootTest
public class ConsultaTeste {
    @Autowired
    public AppointmentRepo consultaRepo;

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

    protected Appointment generateConsulta(Boolean initializeFields){
        Appointment consulta = Appointment.builder()
                .description("desc")
                .date(LocalDate.now())
                .appointmentType(generateTipoConsulta())
                .veterinary(generateVeterinario(true, expertiseRepo, areaRepo))
                .animal(generateAnimal(animalTypeRepo, ownerRepo))
                .build();
        if(initializeFields){
            consulta.setAppointmentType(appointmentTypeRepo.save(consulta.getAppointmentType()));
            consulta.setVeterinary(veterinaryRepo.save(consulta.getVeterinary()));
            consulta.setAnimal(animalRepo.save(consulta.getAnimal()));
        }
        return consulta;
    }

    private void rollback(Appointment consulta, Boolean skipConsulta){
        if (!skipConsulta)
            consultaRepo.delete(consulta);
        veterinaryRepo.delete(consulta.getVeterinary());
        expertiseRepo.delete(consulta.getVeterinary().getExpertise());
        areaRepo.delete(consulta.getVeterinary().getExpertise().getArea());
        animalRepo.delete(consulta.getAnimal());
        animalTypeRepo.delete(consulta.getAnimal().getAnimalType());
        ownerRepo.delete(consulta.getAnimal().getOwner());
        appointmentTypeRepo.delete(consulta.getAppointmentType());
    }

    protected static void rollbackConsulta(Appointment consulta, AppointmentRepo consultaRepo,
                                           VeterinaryRepo veterinaryRepo, ExpertiseRepo expertiseRepo,
                                           AreaRepo areaRepo, AnimalRepo animalRepo,
                                           AnimalTypeRepo animalTypeRepo, OwnerRepo ownerRepo, AppointmentTypeRepo appointmentTypeRepo,
                                           Boolean skipConsulta){
        if (!skipConsulta)
            consultaRepo.delete(consulta);
        veterinaryRepo.delete(consulta.getVeterinary());
        expertiseRepo.delete(consulta.getVeterinary().getExpertise());
        areaRepo.delete(consulta.getVeterinary().getExpertise().getArea());
        animalRepo.delete(consulta.getAnimal());
        animalTypeRepo.delete(consulta.getAnimal().getAnimalType());
        ownerRepo.delete(consulta.getAnimal().getOwner());
        appointmentTypeRepo.delete(consulta.getAppointmentType());
    }

    @Test
    public void deveSalvar(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        Assertions.assertNotNull(consultaSalva);
        rollback(consultaSalva, false);
    }

    @Test
    public void deveAtualizar(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        consultaSalva.setDescription("Nova descrição");
        Appointment consultaAtualizada = consultaRepo.save(consultaSalva);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaSalva.getAppointmentId(), consultaAtualizada.getAppointmentId());
        Assertions.assertEquals(consultaAtualizada.getDescription(), "Nova descrição");
        rollback(consultaSalva, false);
    }

    @Test
    public void deveAtualizarTipoConsulta(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        AppointmentType tipoConsultaAntiga = consultaSalva.getAppointmentType();
        consultaSalva.setAppointmentType(appointmentTypeRepo.save(generateTipoConsulta()));
        Appointment consultaAtualizada = consultaRepo.save(consultaSalva);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaSalva.getAppointmentId(), consultaAtualizada.getAppointmentId());
        Assertions.assertEquals(consultaAtualizada.getAppointmentType().getAppointmentTypeId(), consultaSalva.getAppointmentType().getAppointmentTypeId());
        rollback(consultaSalva, false);
        rollbackTipoConsulta(tipoConsultaAntiga, appointmentTypeRepo);
    }

    @Test
    public void deveAtualizarAnimal(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        Animal animalAntigo = consultaSalva.getAnimal();
        consultaSalva.setAnimal(animalRepo.save(generateAnimal(animalTypeRepo, ownerRepo)));
        Appointment consultaAtualizada = consultaRepo.save(consultaSalva);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaSalva.getAppointmentId(), consultaAtualizada.getAppointmentId());
        Assertions.assertEquals(consultaAtualizada.getAnimal().getAnimalId(), consultaSalva.getAnimal().getAnimalId());
        rollback(consultaSalva, false);
        rollbackAnimal(animalAntigo, animalRepo, animalTypeRepo, ownerRepo);
    }

    @Test
    public void deveAtualizarVeterinario(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        Veterinary veterinarioAntigo = consultaSalva.getVeterinary();
        consultaSalva.setVeterinary(veterinaryRepo.save(generateVeterinario(true, expertiseRepo, areaRepo)));
        Appointment consultaAtualizada = consultaRepo.save(consultaSalva);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaSalva.getAppointmentId(), consultaAtualizada.getAppointmentId());
        Assertions.assertEquals(consultaAtualizada.getVeterinary().getVeterinaryId(), consultaSalva.getVeterinary().getVeterinaryId());
        rollback(consultaSalva, false);
        rollbackVeterinario(veterinarioAntigo, veterinaryRepo, areaRepo, expertiseRepo);
    }

    @Test
    public void deveRemover(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        Long id = consultaSalva.getAppointmentId();
        consultaRepo.deleteById(id);
        Assertions.assertFalse(consultaRepo.findById(id).isPresent());
        rollback(consultaSalva, true);
    }

    @Test
    public void deveBuscar(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        Long id = consultaSalva.getAppointmentId();
        Assertions.assertTrue(consultaRepo.findById(id).isPresent());
        rollback(consultaSalva, false);
    }
}
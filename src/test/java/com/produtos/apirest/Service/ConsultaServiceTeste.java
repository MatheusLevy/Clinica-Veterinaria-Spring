package com.produtos.apirest.Service;

import com.produtos.apirest.models.*;
import com.produtos.apirest.repository.*;
import com.produtos.apirest.service.ConsultaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static com.produtos.apirest.Service.AnimalServiceTeste.generateAnimal;
import static com.produtos.apirest.Service.AnimalServiceTeste.rollbackAnimal;
import static com.produtos.apirest.Service.TipoConsultaServiceTeste.generateTipoConsulta;
import static com.produtos.apirest.Service.TipoConsultaServiceTeste.rollbackTipoConsulta;
import static com.produtos.apirest.Service.VeterinarioServiceTeste.generateVeterinario;
import static com.produtos.apirest.Service.VeterinarioServiceTeste.rollbackVeterinario;


@SpringBootTest
public class ConsultaServiceTeste {

    @Autowired
    public ConsultaService consultaService;

    @Autowired
    public AppointmentRepo consultaRepo;

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

    private Appointment generateConsulta(Boolean initializeFields){
        Appointment consulta = Appointment.builder()
                .description("desc")
                .date(LocalDate.now())
                .appointmentType(generateTipoConsulta())
                .veterinary(generateVeterinario(false, areaRepo, expertiseRepo))
                .animal(generateAnimal(animalTypeRepo, ownerRepo, false))
                .build();
        if (initializeFields){
            consulta.setAppointmentType(appointmentTypeRepo.save(generateTipoConsulta()));
            consulta.setVeterinary(veterinaryRepo.save(generateVeterinario(true, areaRepo, expertiseRepo)));
            consulta.setAnimal(animalRepo.save(generateAnimal(animalTypeRepo, ownerRepo, true)));
        }
        return consulta;
    }

    protected static Appointment generateConsulta(AreaRepo areaRepo, ExpertiseRepo expertiseRepo,
                                               AnimalTypeRepo animalTypeRepo, OwnerRepo ownerRepo,
                                               AppointmentTypeRepo appointmentTypeRepo, VeterinaryRepo veterinaryRepo,
                                               AnimalRepo animalRepo, Boolean initializeFields){
        Appointment consulta = Appointment.builder()
                .description("desc")
                .date(LocalDate.now())
                .appointmentType(generateTipoConsulta())
                .veterinary(generateVeterinario(false, areaRepo, expertiseRepo))
                .animal(generateAnimal(animalTypeRepo, ownerRepo, false))
                .build();
        if (initializeFields){
            consulta.setAppointmentType(appointmentTypeRepo.save(consulta.getAppointmentType()));
            consulta.setVeterinary(veterinaryRepo.save(consulta.getVeterinary()));
            consulta.setAnimal(animalRepo.save(consulta.getAnimal()));
        }
        return consulta;
    }

    private void rollback(Appointment consulta, Boolean skipConsulta){
        if (!skipConsulta)
            consultaRepo.delete(consulta);
        animalRepo.delete(consulta.getAnimal());
        veterinaryRepo.deleteById(consulta.getVeterinary().getVeterinaryId());
        animalTypeRepo.delete(consulta.getAnimal().getAnimalType());
        appointmentTypeRepo.delete(consulta.getAppointmentType());
        expertiseRepo.delete(consulta.getVeterinary().getExpertise());
        areaRepo.delete(consulta.getVeterinary().getExpertise().getArea());
        ownerRepo.delete(consulta.getAnimal().getOwner());
    }

    protected static void rollbackConsulta(Appointment consulta, AppointmentRepo consultaRepo,
                                           AnimalRepo animalRepo, VeterinaryRepo veterinaryRepo,
                                           AnimalTypeRepo animalTypeRepo, AppointmentTypeRepo appointmentTypeRepo,
                                           ExpertiseRepo expertiseRepo, AreaRepo areaRepo,
                                           OwnerRepo ownerRepo, Boolean skipConsulta){
        if (!skipConsulta)
            consultaRepo.delete(consulta);
        animalRepo.delete(consulta.getAnimal());
        veterinaryRepo.deleteById(consulta.getVeterinary().getVeterinaryId());
        animalTypeRepo.delete(consulta.getAnimal().getAnimalType());
        appointmentTypeRepo.delete(consulta.getAppointmentType());
        expertiseRepo.delete(consulta.getVeterinary().getExpertise());
        areaRepo.delete(consulta.getVeterinary().getExpertise().getArea());
        ownerRepo.delete(consulta.getAnimal().getOwner());
    }

    @Test
    public void deveSalvar(){
        Appointment consultaSalva = consultaService.salvar(generateConsulta(true));
        Assertions.assertNotNull(consultaSalva);
        rollback(consultaSalva, false);
    }

    @Test
    public void deveAtualizar(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        consultaSalva.setDescription("Nova Descrição");
        Appointment consultaAtualizada = consultaService.atualizar(consultaSalva);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaSalva.getAppointmentId(), consultaAtualizada.getAppointmentId());
        Assertions.assertEquals(consultaAtualizada.getDescription(), "Nova Descrição");
        rollback(consultaSalva, false);
    }

    @Test
    public void deveRemover(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        Long id = consultaSalva.getAppointmentId();
        consultaService.remover(consultaSalva);
        Assertions.assertFalse(consultaRepo.findById(id).isPresent());
        rollback(consultaSalva, true);
    }

    @Test
    public void deveRemoverComId(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        Long id = consultaSalva.getAppointmentId();
        consultaService.removerPorId(consultaSalva.getAppointmentId());
        Assertions.assertFalse(consultaRepo.findById(id).isPresent());
        rollback(consultaSalva, true);
    }

    @Test
    public void deveRemoverComFeedback(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        Appointment consultaFeedback = consultaService.removerComFeedback(consultaSalva.getAppointmentId());
        Assertions.assertNotNull(consultaFeedback);
        Assertions.assertEquals(consultaFeedback.getAppointmentId(), consultaSalva.getAppointmentId());
        rollback(consultaSalva, true);
    }

    @Test
    public void deveAtualizarVeterinario(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        Veterinary veterinarioAntigo = consultaSalva.getVeterinary();
        Veterinary veterinarioNovo = veterinaryRepo.save(generateVeterinario(true, areaRepo, expertiseRepo));
        Appointment consultaAtualizada = consultaService.atualizarVeterinario(consultaSalva, veterinarioNovo);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaAtualizada.getVeterinary().getVeterinaryId(), veterinarioNovo.getVeterinaryId());
        rollback(consultaAtualizada, false);
        rollbackVeterinario(veterinarioAntigo, veterinaryRepo, areaRepo, expertiseRepo, false);
    }

    @Test
    public void deveAtualizarAnimal(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        Animal animalAntigo = consultaSalva.getAnimal();
        Animal animalNovo = animalRepo.save(generateAnimal(animalTypeRepo, ownerRepo, true));
        Appointment consultaAtualizada = consultaService.atualizarAnimal(consultaSalva, animalNovo);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaAtualizada.getAnimal().getAnimalId(), animalNovo.getAnimalId());
        rollback(consultaAtualizada, false);
        rollbackAnimal(animalAntigo, animalRepo, ownerRepo, animalTypeRepo);
    }

    @Test
    public void deveAtualizarTipoConsulta(){
        Appointment consultaSalva = consultaRepo.save(generateConsulta(true));
        AppointmentType tipoConsultaAntiga = consultaSalva.getAppointmentType();
        AppointmentType tipoConsultaNovo = appointmentTypeRepo.save(generateTipoConsulta());
        Appointment consultaAtualizada = consultaService.atualizarTipoConsulta(consultaSalva, tipoConsultaNovo);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaAtualizada.getAppointmentType().getAppointmentTypeId(), tipoConsultaNovo.getAppointmentTypeId());
        rollback(consultaSalva, false);
        rollbackTipoConsulta(tipoConsultaAntiga, appointmentTypeRepo);
    }

    @Test
    public void deveBuscarPorId(){
        Appointment consultaSalva = consultaService.salvar(generateConsulta(true));
        Appointment consultaEncontrada = consultaService.buscarPorId(consultaSalva.getAppointmentId());
        Assertions.assertNotNull(consultaEncontrada);
        Assertions.assertEquals(consultaEncontrada.getAppointmentId(), consultaSalva.getAppointmentId());
        rollback(consultaSalva, false);
    }

    @Test
    public void deveBuscarTodos(){
        Appointment consultaSalva = consultaService.salvar(generateConsulta(true));
        List<Appointment> consultasList = consultaService.buscarTodos();
        Assertions.assertNotNull(consultasList);
        Assertions.assertFalse(consultasList.isEmpty());
        rollback(consultaSalva, false);
    }
}
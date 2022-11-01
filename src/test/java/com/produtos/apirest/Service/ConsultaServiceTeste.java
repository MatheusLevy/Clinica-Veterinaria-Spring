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
    public ConsultaRepo consultaRepo;

    @Autowired
    public DonoRepo donoRepo;

    @Autowired
    public TipoAnimalRepo tipoAnimalRepo;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public EspecialidadeRepo especialidadeRepo;

    @Autowired
    public VeterinarioRepo veterinarioRepo;

    @Autowired
    public TipoConsultaRepo tipoConsultaRepo;

    private Consulta generateConsulta(Boolean initializeFields){
        Consulta consulta = Consulta.builder()
                .description("desc")
                .date(LocalDate.now())
                .appointmentType(generateTipoConsulta())
                .veterinary(generateVeterinario(false, areaRepo, especialidadeRepo))
                .animal(generateAnimal(tipoAnimalRepo, donoRepo, false))
                .build();
        if (initializeFields){
            consulta.setAppointmentType(tipoConsultaRepo.save(generateTipoConsulta()));
            consulta.setVeterinary(veterinarioRepo.save(generateVeterinario(true, areaRepo, especialidadeRepo)));
            consulta.setAnimal(animalRepo.save(generateAnimal(tipoAnimalRepo, donoRepo, true)));
        }
        return consulta;
    }

    protected static Consulta generateConsulta(AreaRepo areaRepo, EspecialidadeRepo especialidadeRepo,
                                               TipoAnimalRepo tipoAnimalRepo, DonoRepo donoRepo,
                                               TipoConsultaRepo tipoConsultaRepo, VeterinarioRepo veterinarioRepo,
                                               AnimalRepo animalRepo, Boolean initializeFields){
        Consulta consulta = Consulta.builder()
                .description("desc")
                .date(LocalDate.now())
                .appointmentType(generateTipoConsulta())
                .veterinary(generateVeterinario(false, areaRepo, especialidadeRepo))
                .animal(generateAnimal(tipoAnimalRepo, donoRepo, false))
                .build();
        if (initializeFields){
            consulta.setAppointmentType(tipoConsultaRepo.save(consulta.getAppointmentType()));
            consulta.setVeterinary(veterinarioRepo.save(consulta.getVeterinary()));
            consulta.setAnimal(animalRepo.save(consulta.getAnimal()));
        }
        return consulta;
    }

    private void rollback(Consulta consulta, Boolean skipConsulta){
        if (!skipConsulta)
            consultaRepo.delete(consulta);
        animalRepo.delete(consulta.getAnimal());
        veterinarioRepo.deleteById(consulta.getVeterinary().getVeterinaryId());
        tipoAnimalRepo.delete(consulta.getAnimal().getAnimalType());
        tipoConsultaRepo.delete(consulta.getAppointmentType());
        especialidadeRepo.delete(consulta.getVeterinary().getExpertise());
        areaRepo.delete(consulta.getVeterinary().getExpertise().getArea());
        donoRepo.delete(consulta.getAnimal().getOwner());
    }

    protected static void rollbackConsulta(Consulta consulta, ConsultaRepo consultaRepo,
                                           AnimalRepo animalRepo, VeterinarioRepo veterinarioRepo,
                                           TipoAnimalRepo tipoAnimalRepo, TipoConsultaRepo tipoConsultaRepo,
                                           EspecialidadeRepo especialidadeRepo, AreaRepo areaRepo,
                                           DonoRepo donoRepo, Boolean skipConsulta){
        if (!skipConsulta)
            consultaRepo.delete(consulta);
        animalRepo.delete(consulta.getAnimal());
        veterinarioRepo.deleteById(consulta.getVeterinary().getVeterinaryId());
        tipoAnimalRepo.delete(consulta.getAnimal().getAnimalType());
        tipoConsultaRepo.delete(consulta.getAppointmentType());
        especialidadeRepo.delete(consulta.getVeterinary().getExpertise());
        areaRepo.delete(consulta.getVeterinary().getExpertise().getArea());
        donoRepo.delete(consulta.getAnimal().getOwner());
    }

    @Test
    public void deveSalvar(){
        Consulta consultaSalva = consultaService.salvar(generateConsulta(true));
        Assertions.assertNotNull(consultaSalva);
        rollback(consultaSalva, false);
    }

    @Test
    public void deveAtualizar(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        consultaSalva.setDescription("Nova Descrição");
        Consulta consultaAtualizada = consultaService.atualizar(consultaSalva);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaSalva.getConsultaId(), consultaAtualizada.getConsultaId());
        Assertions.assertEquals(consultaAtualizada.getDescription(), "Nova Descrição");
        rollback(consultaSalva, false);
    }

    @Test
    public void deveRemover(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        Long id = consultaSalva.getConsultaId();
        consultaService.remover(consultaSalva);
        Assertions.assertFalse(consultaRepo.findById(id).isPresent());
        rollback(consultaSalva, true);
    }

    @Test
    public void deveRemoverComId(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        Long id = consultaSalva.getConsultaId();
        consultaService.removerPorId(consultaSalva.getConsultaId());
        Assertions.assertFalse(consultaRepo.findById(id).isPresent());
        rollback(consultaSalva, true);
    }

    @Test
    public void deveRemoverComFeedback(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        Consulta consultaFeedback = consultaService.removerComFeedback(consultaSalva.getConsultaId());
        Assertions.assertNotNull(consultaFeedback);
        Assertions.assertEquals(consultaFeedback.getConsultaId(), consultaSalva.getConsultaId());
        rollback(consultaSalva, true);
    }

    @Test
    public void deveAtualizarVeterinario(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        Veterinario veterinarioAntigo = consultaSalva.getVeterinary();
        Veterinario veterinarioNovo = veterinarioRepo.save(generateVeterinario(true, areaRepo, especialidadeRepo));
        Consulta consultaAtualizada = consultaService.atualizarVeterinario(consultaSalva, veterinarioNovo);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaAtualizada.getVeterinary().getVeterinaryId(), veterinarioNovo.getVeterinaryId());
        rollback(consultaAtualizada, false);
        rollbackVeterinario(veterinarioAntigo, veterinarioRepo, areaRepo, especialidadeRepo, false);
    }

    @Test
    public void deveAtualizarAnimal(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        Animal animalAntigo = consultaSalva.getAnimal();
        Animal animalNovo = animalRepo.save(generateAnimal(tipoAnimalRepo, donoRepo, true));
        Consulta consultaAtualizada = consultaService.atualizarAnimal(consultaSalva, animalNovo);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaAtualizada.getAnimal().getAnimalId(), animalNovo.getAnimalId());
        rollback(consultaAtualizada, false);
        rollbackAnimal(animalAntigo, animalRepo, donoRepo, tipoAnimalRepo);
    }

    @Test
    public void deveAtualizarTipoConsulta(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        TipoConsulta tipoConsultaAntiga = consultaSalva.getAppointmentType();
        TipoConsulta tipoConsultaNovo = tipoConsultaRepo.save(generateTipoConsulta());
        Consulta consultaAtualizada = consultaService.atualizarTipoConsulta(consultaSalva, tipoConsultaNovo);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaAtualizada.getAppointmentType().getAppointmentTypeId(), tipoConsultaNovo.getAppointmentTypeId());
        rollback(consultaSalva, false);
        rollbackTipoConsulta(tipoConsultaAntiga, tipoConsultaRepo);
    }

    @Test
    public void deveBuscarPorId(){
        Consulta consultaSalva = consultaService.salvar(generateConsulta(true));
        Consulta consultaEncontrada = consultaService.buscarPorId(consultaSalva.getConsultaId());
        Assertions.assertNotNull(consultaEncontrada);
        Assertions.assertEquals(consultaEncontrada.getConsultaId(), consultaSalva.getConsultaId());
        rollback(consultaSalva, false);
    }

    @Test
    public void deveBuscarTodos(){
        Consulta consultaSalva = consultaService.salvar(generateConsulta(true));
        List<Consulta> consultasList = consultaService.buscarTodos();
        Assertions.assertNotNull(consultasList);
        Assertions.assertFalse(consultasList.isEmpty());
        rollback(consultaSalva, false);
    }
}
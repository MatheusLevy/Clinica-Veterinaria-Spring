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
    public ConsultaRepo consultaRepo;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public DonoRepo donoRepo;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public EspecialidadeRepo especialidadeRepo;

    @Autowired
    public TipoConsultaRepo tipoConsultaRepo;

    @Autowired
    public TipoAnimalRepo tipoAnimalRepo;

    @Autowired
    public VeterinarioRepo veterinarioRepo;

    protected Consulta generateConsulta(Boolean initializeFields){
        Consulta consulta = Consulta.builder()
                .descricao("desc")
                .data(LocalDate.now())
                .tipoConsulta(generateTipoConsulta())
                .veterinario(generateVeterinario(true, especialidadeRepo, areaRepo))
                .animal(generateAnimal(tipoAnimalRepo, donoRepo))
                .build();
        if(initializeFields){
            consulta.setTipoConsulta(tipoConsultaRepo.save(consulta.getTipoConsulta()));
            consulta.setVeterinario(veterinarioRepo.save(consulta.getVeterinario()));
            consulta.setAnimal(animalRepo.save(consulta.getAnimal()));
        }
        return consulta;
    }

    private void rollback(Consulta consulta, Boolean skipConsulta){
        if (!skipConsulta)
            consultaRepo.delete(consulta);
        veterinarioRepo.delete(consulta.getVeterinario());
        especialidadeRepo.delete(consulta.getVeterinario().getEspecialidade());
        areaRepo.delete(consulta.getVeterinario().getEspecialidade().getArea());
        animalRepo.delete(consulta.getAnimal());
        tipoAnimalRepo.delete(consulta.getAnimal().getTipoAnimal());
        donoRepo.delete(consulta.getAnimal().getDono());
        tipoConsultaRepo.delete(consulta.getTipoConsulta());
    }

    protected static void rollbackConsulta(Consulta consulta, ConsultaRepo consultaRepo,
                                           VeterinarioRepo veterinarioRepo, EspecialidadeRepo especialidadeRepo,
                                           AreaRepo areaRepo, AnimalRepo animalRepo,
                                           TipoAnimalRepo tipoAnimalRepo, DonoRepo donoRepo, TipoConsultaRepo tipoConsultaRepo,
                                           Boolean skipConsulta){
        if (!skipConsulta)
            consultaRepo.delete(consulta);
        veterinarioRepo.delete(consulta.getVeterinario());
        especialidadeRepo.delete(consulta.getVeterinario().getEspecialidade());
        areaRepo.delete(consulta.getVeterinario().getEspecialidade().getArea());
        animalRepo.delete(consulta.getAnimal());
        tipoAnimalRepo.delete(consulta.getAnimal().getTipoAnimal());
        donoRepo.delete(consulta.getAnimal().getDono());
        tipoConsultaRepo.delete(consulta.getTipoConsulta());
    }

    @Test
    public void deveSalvar(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        Assertions.assertNotNull(consultaSalva);
        rollback(consultaSalva, false);
    }

    @Test
    public void deveAtualizar(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        consultaSalva.setDescricao("Nova descrição");
        Consulta consultaAtualizada = consultaRepo.save(consultaSalva);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaSalva.getConsultaId(), consultaAtualizada.getConsultaId());
        Assertions.assertEquals(consultaAtualizada.getDescricao(), "Nova descrição");
        rollback(consultaSalva, false);
    }

    @Test
    public void deveAtualizarTipoConsulta(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        TipoConsulta tipoConsultaAntiga = consultaSalva.getTipoConsulta();
        consultaSalva.setTipoConsulta(tipoConsultaRepo.save(generateTipoConsulta()));
        Consulta consultaAtualizada = consultaRepo.save(consultaSalva);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaSalva.getConsultaId(), consultaAtualizada.getConsultaId());
        Assertions.assertEquals(consultaAtualizada.getTipoConsulta().getTipoConsultaId(), consultaSalva.getTipoConsulta().getTipoConsultaId());
        rollback(consultaSalva, false);
        rollbackTipoConsulta(tipoConsultaAntiga, tipoConsultaRepo);
    }

    @Test
    public void deveAtualizarAnimal(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        Animal animalAntigo = consultaSalva.getAnimal();
        consultaSalva.setAnimal(animalRepo.save(generateAnimal(tipoAnimalRepo, donoRepo)));
        Consulta consultaAtualizada = consultaRepo.save(consultaSalva);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaSalva.getConsultaId(), consultaAtualizada.getConsultaId());
        Assertions.assertEquals(consultaAtualizada.getAnimal().getAnimalId(), consultaSalva.getAnimal().getAnimalId());
        rollback(consultaSalva, false);
        rollbackAnimal(animalAntigo, animalRepo, tipoAnimalRepo, donoRepo);
    }

    @Test
    public void deveAtualizarVeterinario(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        Veterinario veterinarioAntigo = consultaSalva.getVeterinario();
        consultaSalva.setVeterinario(veterinarioRepo.save(generateVeterinario(true, especialidadeRepo, areaRepo)));
        Consulta consultaAtualizada = consultaRepo.save(consultaSalva);
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaSalva.getConsultaId(), consultaAtualizada.getConsultaId());
        Assertions.assertEquals(consultaAtualizada.getVeterinario().getVeterinarioId(), consultaSalva.getVeterinario().getVeterinarioId());
        rollback(consultaSalva, false);
        rollbackVeterinario(veterinarioAntigo, veterinarioRepo, areaRepo, especialidadeRepo);
    }

    @Test
    public void deveRemover(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        Long id = consultaSalva.getConsultaId();
        consultaRepo.deleteById(id);
        Assertions.assertFalse(consultaRepo.findById(id).isPresent());
        rollback(consultaSalva, true);
    }

    @Test
    public void deveBuscar(){
        Consulta consultaSalva = consultaRepo.save(generateConsulta(true));
        Long id = consultaSalva.getConsultaId();
        Assertions.assertTrue(consultaRepo.findById(id).isPresent());
        rollback(consultaSalva, false);
    }
}
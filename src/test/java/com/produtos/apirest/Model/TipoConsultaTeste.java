package com.produtos.apirest.Model;

import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.repository.TipoConsultaRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TipoConsultaTeste {
    @Autowired
    public TipoConsultaRepo tipoConsultaRepo;

    public static TipoConsulta generateTipoConsulta(){
       return TipoConsulta.builder()
                .name("name")
                .build();
    }

    private void rollback(TipoConsulta tipoConsulta){
        tipoConsultaRepo.delete(tipoConsulta);
    }

    protected static void rollbackTipoConsulta(TipoConsulta tipoConsulta, TipoConsultaRepo tipoConsultaRepo){
        tipoConsultaRepo.delete(tipoConsulta);
    }

    @Test
    public void deveSalvar(){
        TipoConsulta tipoConsultaSalva = tipoConsultaRepo.save(generateTipoConsulta());
        Assertions.assertNotNull(tipoConsultaSalva);
        Assertions.assertEquals(generateTipoConsulta().getName(), tipoConsultaSalva.getName());
        rollback(tipoConsultaSalva);
    }

    @Test
    public void deveAtualizar(){
        TipoConsulta tipoConsultaSalva = tipoConsultaRepo.save(generateTipoConsulta());
        tipoConsultaSalva.setName("Nome Novo");
        TipoConsulta tipoConsultaAtualizada =  tipoConsultaRepo.save(tipoConsultaSalva);
        Assertions.assertNotNull(tipoConsultaAtualizada);
        Assertions.assertEquals(tipoConsultaAtualizada.getAppointmentTypeId(), tipoConsultaSalva.getAppointmentTypeId());
        Assertions.assertEquals(tipoConsultaAtualizada.getName(), "Nome Novo");
        rollback(tipoConsultaAtualizada);
    }

    @Test
    public void deveRemover(){
        TipoConsulta tipoConsultaSalva = tipoConsultaRepo.save(generateTipoConsulta());
        Long id = tipoConsultaSalva.getAppointmentTypeId();
        tipoConsultaRepo.deleteById(id);
        Assertions.assertFalse(tipoConsultaRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscar(){
        TipoConsulta tipoConsultaSalva = tipoConsultaRepo.save(generateTipoConsulta());
        Long id = tipoConsultaSalva.getAppointmentTypeId();
        Assertions.assertTrue(tipoConsultaRepo.findById(id).isPresent());
        rollback(tipoConsultaSalva);
    }
}
package com.produtos.apirest.Model;

import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.repository.AppointmentTypeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TipoConsultaTeste {
    @Autowired
    public AppointmentTypeRepo appointmentTypeRepo;

    public static AppointmentType generateTipoConsulta(){
       return AppointmentType.builder()
                .name("name")
                .build();
    }

    private void rollback(AppointmentType tipoConsulta){
        appointmentTypeRepo.delete(tipoConsulta);
    }

    protected static void rollbackTipoConsulta(AppointmentType tipoConsulta, AppointmentTypeRepo appointmentTypeRepo){
        appointmentTypeRepo.delete(tipoConsulta);
    }

    @Test
    public void deveSalvar(){
        AppointmentType tipoConsultaSalva = appointmentTypeRepo.save(generateTipoConsulta());
        Assertions.assertNotNull(tipoConsultaSalva);
        Assertions.assertEquals(generateTipoConsulta().getName(), tipoConsultaSalva.getName());
        rollback(tipoConsultaSalva);
    }

    @Test
    public void deveAtualizar(){
        AppointmentType tipoConsultaSalva = appointmentTypeRepo.save(generateTipoConsulta());
        tipoConsultaSalva.setName("Nome Novo");
        AppointmentType tipoConsultaAtualizada =  appointmentTypeRepo.save(tipoConsultaSalva);
        Assertions.assertNotNull(tipoConsultaAtualizada);
        Assertions.assertEquals(tipoConsultaAtualizada.getAppointmentTypeId(), tipoConsultaSalva.getAppointmentTypeId());
        Assertions.assertEquals(tipoConsultaAtualizada.getName(), "Nome Novo");
        rollback(tipoConsultaAtualizada);
    }

    @Test
    public void deveRemover(){
        AppointmentType tipoConsultaSalva = appointmentTypeRepo.save(generateTipoConsulta());
        Long id = tipoConsultaSalva.getAppointmentTypeId();
        appointmentTypeRepo.deleteById(id);
        Assertions.assertFalse(appointmentTypeRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscar(){
        AppointmentType tipoConsultaSalva = appointmentTypeRepo.save(generateTipoConsulta());
        Long id = tipoConsultaSalva.getAppointmentTypeId();
        Assertions.assertTrue(appointmentTypeRepo.findById(id).isPresent());
        rollback(tipoConsultaSalva);
    }
}
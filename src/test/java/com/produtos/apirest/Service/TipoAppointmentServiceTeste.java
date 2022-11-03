package com.produtos.apirest.Service;

import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.repository.AppointmentTypeRepo;
import com.produtos.apirest.service.AppointmentTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TipoAppointmentServiceTeste {

    @Autowired
    public AppointmentTypeService appointmentTypeService;

    @Autowired
    public AppointmentTypeRepo appointmentTypeRepo;

    protected static AppointmentType generateTipoConsulta(){
        return AppointmentType.builder()
                .name("Test")
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
        AppointmentType tipoConsultaSalva = appointmentTypeService.save(generateTipoConsulta());
        Assertions.assertNotNull(tipoConsultaSalva);
        rollback(tipoConsultaSalva);
    }

    @Test
    public void deveAtualizar(){
        AppointmentType tipoConsultaSalva = appointmentTypeRepo.save(generateTipoConsulta());
        tipoConsultaSalva.setName("Tipo Consulta Atualizado");
        AppointmentType tipoConsultaAtualizada = appointmentTypeRepo.save(tipoConsultaSalva);
        Assertions.assertNotNull(tipoConsultaAtualizada);
        Assertions.assertEquals(tipoConsultaSalva.getAppointmentTypeId(), tipoConsultaAtualizada.getAppointmentTypeId());
        rollback(tipoConsultaAtualizada);
    }

    @Test
    public void deveRemover(){
        AppointmentType tipoConsultaSalvo = appointmentTypeRepo.save(generateTipoConsulta());
        Long id = tipoConsultaSalvo.getAppointmentTypeId();
        appointmentTypeService.removeById(id);
        Assertions.assertFalse(appointmentTypeRepo.findById(id).isPresent());
    }

    @Test
    public void deveRemoverComFeedback(){
        AppointmentType tipoConsultaSalvo = appointmentTypeRepo.save(generateTipoConsulta());
        AppointmentType tipoConsultaFeedback = appointmentTypeService.removeByIdWithFeedback(tipoConsultaSalvo.getAppointmentTypeId());
        Assertions.assertNotNull(tipoConsultaFeedback);
    }

    @Test
    public void deveBuscarPorId(){
        AppointmentType tipoConsultaSalvo = appointmentTypeRepo.save(generateTipoConsulta());
        AppointmentType tipoConsultaEncontrado = appointmentTypeService.findById(tipoConsultaSalvo.getAppointmentTypeId());
        Assertions.assertNotNull(tipoConsultaEncontrado);
        Assertions.assertEquals(tipoConsultaSalvo.getAppointmentTypeId(), tipoConsultaEncontrado.getAppointmentTypeId());
        rollback(tipoConsultaSalvo);
    }

    @Test
    public void deveBuscarComFiltro(){
        AppointmentType tipoConsultaSalvo = appointmentTypeRepo.save(generateTipoConsulta());
        Assertions.assertFalse(appointmentTypeService.find(tipoConsultaSalvo).isEmpty());
        rollback(tipoConsultaSalvo);
    }

    @Test
    public void deveBuscarTodos(){
        AppointmentType tipoConsultaSalvo = appointmentTypeRepo.save(generateTipoConsulta());
        List<AppointmentType> tiposList = appointmentTypeService.findAll();
        Assertions.assertNotNull(tiposList);
        Assertions.assertFalse(tiposList.isEmpty());
        rollback(tipoConsultaSalvo);
    }
}
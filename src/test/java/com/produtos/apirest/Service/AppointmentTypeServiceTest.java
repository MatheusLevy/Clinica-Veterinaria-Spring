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
public class AppointmentTypeServiceTest {

    @Autowired
    public AppointmentTypeService appointmentTypeService;

    @Autowired
    public AppointmentTypeRepo appointmentTypeRepo;

    protected static AppointmentType generateAppointmentType(){
        return AppointmentType.builder()
                .name("name")
                .build();
    }

    private void rollback(AppointmentType appointmentType){
        appointmentTypeRepo.delete(appointmentType);
    }

    protected static void rollbackAppointmentType(AppointmentType appointmentType, AppointmentTypeRepo appointmentTypeRepo){
        appointmentTypeRepo.delete(appointmentType);
    }

    @Test
    public void save(){
        AppointmentType typeSaved = appointmentTypeService.save(generateAppointmentType());
        Assertions.assertNotNull(typeSaved);
        rollback(typeSaved);
    }

    @Test
    public void update(){
        AppointmentType typeSaved = appointmentTypeRepo.save(generateAppointmentType());
        typeSaved.setName("New Name");
        AppointmentType typeUpdated = appointmentTypeRepo.save(typeSaved);
        Assertions.assertNotNull(typeUpdated);
        Assertions.assertEquals(typeSaved.getAppointmentTypeId(), typeUpdated.getAppointmentTypeId());
        rollback(typeUpdated);
    }

    @Test
    public void removeById(){
        AppointmentType typeSaved = appointmentTypeRepo.save(generateAppointmentType());
        Long id = typeSaved.getAppointmentTypeId();
        appointmentTypeService.removeById(id);
        Assertions.assertNull(appointmentTypeRepo.findByAppointmentTypeId(id));
    }

    @Test
    public void removeByIdWithFeedback(){
        AppointmentType typeSaved = appointmentTypeRepo.save(generateAppointmentType());
        AppointmentType feedback = appointmentTypeService.removeByIdWithFeedback(typeSaved.getAppointmentTypeId());
        Assertions.assertNotNull(feedback);
    }

    @Test
    public void findById(){
        AppointmentType typeSaved = appointmentTypeRepo.save(generateAppointmentType());
        AppointmentType typeFind = appointmentTypeService.findById(typeSaved.getAppointmentTypeId());
        Assertions.assertNotNull(typeFind);
        Assertions.assertEquals(typeSaved.getAppointmentTypeId(), typeFind.getAppointmentTypeId());
        rollback(typeSaved);
    }

    @Test
    public void find(){
        AppointmentType typeSaved = appointmentTypeRepo.save(generateAppointmentType());
        Assertions.assertFalse(appointmentTypeService.find(typeSaved).isEmpty());
        rollback(typeSaved);
    }

    @Test
    public void findAll(){
        AppointmentType typeSaved = appointmentTypeRepo.save(generateAppointmentType());
        List<AppointmentType> types = appointmentTypeService.findAll();
        Assertions.assertNotNull(types);
        Assertions.assertFalse(types.isEmpty());
        rollback(typeSaved);
    }
}
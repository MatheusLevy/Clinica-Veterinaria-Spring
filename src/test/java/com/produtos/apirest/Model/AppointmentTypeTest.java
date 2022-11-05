package com.produtos.apirest.Model;

import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.repository.AppointmentTypeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppointmentTypeTest {
    @Autowired
    public AppointmentTypeRepo appointmentTypeRepo;

    public static AppointmentType generateAppointmentType(){
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
        AppointmentType appointmentTypeSaved = appointmentTypeRepo.save(generateAppointmentType());
        Assertions.assertNotNull(appointmentTypeSaved);
        Assertions.assertEquals(generateAppointmentType().getName(), appointmentTypeSaved.getName());
        rollback(appointmentTypeSaved);
    }

    @Test
    public void update(){
        AppointmentType typeSaved = appointmentTypeRepo.save(generateAppointmentType());
        typeSaved.setName("New Name");
        AppointmentType typeUpdated =  appointmentTypeRepo.save(typeSaved);
        Assertions.assertNotNull(typeUpdated);
        Assertions.assertEquals(typeUpdated.getAppointmentTypeId(), typeSaved.getAppointmentTypeId());
        Assertions.assertEquals(typeUpdated.getName(), "New Name");
        rollback(typeUpdated);
    }

    @Test
    public void removeById(){
        AppointmentType typeSaved = appointmentTypeRepo.save(generateAppointmentType());
        Long id = typeSaved.getAppointmentTypeId();
        appointmentTypeRepo.deleteById(id);
        Assertions.assertNull(appointmentTypeRepo.findByAppointmentTypeId(id));
    }

    @Test
    public void findById(){
        AppointmentType typeSaved = appointmentTypeRepo.save(generateAppointmentType());
        Long id = typeSaved.getAppointmentTypeId();
        Assertions.assertNotNull(appointmentTypeRepo.findByAppointmentTypeId(id));
        rollback(typeSaved);
    }
}
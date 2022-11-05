package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Appointment;
import com.produtos.apirest.models.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentTypeDTO {

    private Long id;
    private String name;
    private List<Appointment> appointments;
    private List<AppointmentType> appointmentTypes;

    private void hasId(){
        if (this.id == null){
            this.id = 0L;
        }
    }

    public AppointmentType toAppointment(){
        return AppointmentType.builder()
                .appointmentTypeId(this.id)
                .name(this.name)
                .appointments(this.appointments)
                .build();
    }
}
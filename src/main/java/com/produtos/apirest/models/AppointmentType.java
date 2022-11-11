package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.AppointmentTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "appointment_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long appointmentTypeId;

    @Column(name = "name")
    @NotNull
    private String name;

    @OneToMany(mappedBy = "appointmentType", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Appointment> appointments;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + appointmentTypeId + ", name= " + name + " ]" ;
    }

    public AppointmentTypeDTO toAppointmentTypeDTO(){
        return AppointmentTypeDTO.builder()
                .id(this.appointmentTypeId)
                .name(this.name)
                .build();
    }
}
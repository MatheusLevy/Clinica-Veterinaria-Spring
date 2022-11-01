package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.AppointmentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "appointment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long appointmentId;

    @ManyToOne
    @JoinColumn(name="veterinary_id", nullable = false)
    @JsonIgnore
    private Veterinary veterinary;

    @ManyToOne
    @JoinColumn(name="animal_id", nullable = false)
    @JsonIgnore
    private Animal animal;

    @ManyToOne
    @JoinColumn(name="appointmentType_id", nullable = false)
    @JsonIgnore
    private AppointmentType appointmentType;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + appointmentId + ", veterinary= "
                + veterinary + ", animal= " + animal + ", Appointment Type= " + appointmentType
                + ", description= " + description + ", date= " + date + " ]";
    }

    public AppointmentDTO toAppointmentDTO(){
        return AppointmentDTO.builder()
                .id(this.appointmentId)
                .date(this.date)
                .description(this.description)
                .vetId(this.veterinary.getVeterinaryId())
                .vetName(this.veterinary.getName())
                .animalId(this.animal.getAnimalId())
                .animalName(this.animal.getName())
                .appointmentTypeId(this.appointmentType.getAppointmentTypeId())
                .typeName(this.appointmentType.getName())
                .build();
    }
}
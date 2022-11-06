package com.produtos.apirest.models.DTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Appointment;
import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.models.Veterinary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Convert;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private Long id;
    private Veterinary veterinary;
    private String vetName;
    private Long vetId;
    private List<Veterinary> vets;
    private Animal animal;
    private String animalName;
    private Long animalId;
    private List<Animal> animals;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDate date;
    private AppointmentType type;
    private String typeName;
    private Long appointmentTypeId;
    private List<AppointmentType> appointmentTypes;

    private void hasId(){
        if (this.id == null)
            this.id = 0L;
    }

    public Appointment toAppointment(){
        return Appointment.builder()
                .appointmentId(this.id)
                .date(this.date)
                .description(this.description)
                .animal(this.animal)
                .veterinary(this.veterinary)
                .appointmentType(this.type)
                .build();
    }

    public Appointment toAppointment(Animal animal, Veterinary vet, AppointmentType appointmentType){
        hasId();
        return Appointment.builder()
                .appointmentId(this.id)
                .date(this.date)
                .description(this.description)
                .animal(animal)
                .veterinary(vet)
                .appointmentType(appointmentType)
                .build();
    }
}

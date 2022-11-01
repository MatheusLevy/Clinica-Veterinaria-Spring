package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.ConsultaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "consulta")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long consultaId;

    @ManyToOne
    @JoinColumn(name="veterinary_id", nullable = false)
    @JsonIgnore
    private Veterinario veterinary;

    @ManyToOne
    @JoinColumn(name="animal_id", nullable = false)
    @JsonIgnore
    private Animal animal;

    @ManyToOne
    @JoinColumn(name="appointmentType_id", nullable = false)
    @JsonIgnore
    private TipoConsulta appointmentType;

    @Column(name = "description")
    private String description;

    @Column(name = "data")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + consultaId + ", veterinary= "
                + veterinary + ", animal= " + animal + ", Appointment Type= " + appointmentType
                + ", description= " + description + ", date= " + date + " ]";
    }

    public ConsultaDTO toConsultaDTO(){
        return ConsultaDTO.builder()
                .id(this.consultaId)
                .data(this.date)
                .descricao(this.description)
                .veterinarioId(this.veterinary.getVeterinaryId())
                .veterinarioNome(this.veterinary.getName())
                .animalId(this.animal.getAnimalId())
                .animalNome(this.animal.getName())
                .tipoConsultaId(this.appointmentType.getAppointmentTypeId())
                .tipoNome(this.appointmentType.getName())
                .build();
    }
}
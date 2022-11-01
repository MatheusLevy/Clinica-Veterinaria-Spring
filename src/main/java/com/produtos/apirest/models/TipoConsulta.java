package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.TipoConsultaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "appointment_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoConsulta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long appointmentTypeId;

    @Column(name = "name")
    @NotNull
    private String name;

    @OneToMany(mappedBy = "appointmentType", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Consulta> appointments;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + appointmentTypeId + ", name= " + name + " ]" ;
    }

    public TipoConsultaDTO toTipoConsultaDTO(){
        return TipoConsultaDTO.builder()
                .id(this.appointmentTypeId)
                .nome(this.name)
                .build();
    }
}
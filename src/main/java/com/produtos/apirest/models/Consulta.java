package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
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
    @JoinColumn(name="veterinario_id", nullable = false)
    @JsonIgnore
    private Veterinario veterinario;

    @ManyToOne
    @JoinColumn(name="animal_id", nullable = false)
    @JsonIgnore
    private Animal animal;

    @ManyToOne
    @JoinColumn(name="consultatipo_id", nullable = false)
    @JsonIgnore
    private TipoConsulta tipoConsulta;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + consultaId + ", veterinario= "
                + veterinario + ", animal= " + animal + ", tipo Consulta= " + tipoConsulta
                + ", descrição= " + descricao + ", data= " + data + " ]";
    }

}

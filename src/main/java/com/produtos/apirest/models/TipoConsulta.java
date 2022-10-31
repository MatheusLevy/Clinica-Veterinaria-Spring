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
@Table(name = "tipo_consulta")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoConsulta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long tipoConsultaId;

    @Column(name = "nome")
    @NotNull
    private String nome;

    @OneToMany(mappedBy = "tipoConsulta", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Consulta> consultas;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + tipoConsultaId + ", nome= " + nome+ " ]" ;
    }

    public TipoConsultaDTO toTipoConsultaDTO(){
        return TipoConsultaDTO.builder()
                .id(this.tipoConsultaId)
                .nome(this.nome)
                .build();
    }
}
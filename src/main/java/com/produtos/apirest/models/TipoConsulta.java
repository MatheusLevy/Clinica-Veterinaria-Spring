package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String nome;

    @OneToMany(mappedBy = "tipoConsulta")
    @JsonIgnore
    private List<Consulta> consultas;

}

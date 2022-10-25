package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.AreaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="area")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long areaId;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Especialidade> especialidades;

    public AreaDTO toDTO(){
        return AreaDTO.builder()
                .id(this.areaId)
                .nome(this.nome)
                .build();
    }
}
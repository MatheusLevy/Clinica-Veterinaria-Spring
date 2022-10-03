package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="especialidade")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long especialidadeId;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    @JoinColumn(name="area_id", nullable = false)
    private Area area;

    @OneToMany(mappedBy = "especialidade")
    @JsonIgnore
    private List<Veterinario> veterinarios;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + especialidadeId + ", nome= " + nome + ", area= "+ area.getNome() + "]";
    }
}

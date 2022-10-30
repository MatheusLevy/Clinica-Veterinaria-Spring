package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.TipoAnimalDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_animal")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoAnimal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long tipoAnimalId;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "tipoAnimal", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Animal> animais;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + tipoAnimalId + ", nome= " + nome + "]";
    }

    public TipoAnimalDTO toTipoAnimalDTO(){
        return TipoAnimalDTO.builder()
                .id(this.tipoAnimalId)
                .nome(this.nome)
                .build();
    }
}
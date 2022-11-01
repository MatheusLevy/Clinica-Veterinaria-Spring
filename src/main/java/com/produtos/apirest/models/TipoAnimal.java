package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.TipoAnimalDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "animal_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoAnimal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long animalTypeId;

    @Column(name = "name")
    @NotNull
    private String name;

    @OneToMany(mappedBy = "animalType", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Animal> animals;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + animalTypeId + ", name= " + name + "]";
    }

    public TipoAnimalDTO toTipoAnimalDTO(){
        return TipoAnimalDTO.builder()
                .id(this.animalTypeId)
                .nome(this.name)
                .build();
    }
}
package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.AnimalTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "animal_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalType {

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

    public AnimalTypeDTO toAnimalTypeDTO(){
        return AnimalTypeDTO.builder()
                .id(this.animalTypeId)
                .name(this.name)
                .build();
    }
}
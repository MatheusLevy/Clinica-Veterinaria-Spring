package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.AnimalDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "animal")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long animalId;

    @Column(name = "name")
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name="type_id", nullable = false)
    private AnimalType animalType;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @OneToMany(mappedBy = "animal")
    @JsonIgnore
    private List<Appointment> appointments;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id= "+ animalId +", name= " + name +", type= " + animalType + ", owner= " + owner + "]";
    }

    public AnimalDTO toAnimalDTO(){
        return AnimalDTO.builder()
                .id(this.animalId)
                .animalTypeId(this.animalType.getAnimalTypeId())
                .animalTypeName(this.animalType.getName())
                .ownerName(this.owner.getName())
                .ownerId(this.owner.getOwnerId())
                .build();
    }
}
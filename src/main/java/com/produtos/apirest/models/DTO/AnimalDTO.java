package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.models.Owner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO {
    private Long id;
    private String name;
    private Long ownerId;
    private Owner owner;
    private String ownerName;
    private List<Owner> owners;
    private AnimalType type;
    private Long animalTypeId;
    private String animalTypeName;
    private List<AnimalType> types;

    private void hasId(){
        if (this.id == null)
            this.id = 0L;
    }

    private void hasName(){
        if (this.name == null)
            this.name = "";
    }

    public Animal toAnimal(){
        hasId();
        hasName();
        return Animal.builder()
                .animalId(this.id)
                .name(this.name)
                .owner(this.owner)
                .animalType(this.type)
                .build();
    }

    public Animal toAnimal(Owner owner, AnimalType type){
        hasId();
        hasName();
        return Animal.builder()
                .animalId(this.id)
                .name(this.name)
                .owner(owner)
                .animalType(type)
                .build();
    }
}
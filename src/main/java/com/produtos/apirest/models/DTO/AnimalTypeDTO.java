package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalTypeDTO {

    private Long id;
    private String name;
    private List<Animal> animals;
    private List<AnimalType> animalTypes;

    public AnimalType toAnimalType(){
        return AnimalType.builder()
                .animalTypeId(this.id)
                .name(this.name)
                .animals(this.animals)
                .build();
    }
}
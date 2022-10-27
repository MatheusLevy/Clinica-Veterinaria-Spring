package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.TipoAnimal;
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
    private String nome;

    private Long idDono;
    private Dono dono;
    private String nomeDono;
    private List<Dono> donos;

    private TipoAnimal tipo;
    private Long idTipoAnimal;
    private String nomeTipoAnimal;
    private List<TipoAnimal> tipos;

    public Animal toAnimal(){
        return Animal.builder()
                .animalId(this.id)
                .nome(this.nome)
                .dono(this.dono)
                .tipoAnimal(this.tipo)
                .build();
    }

    public Animal toAnimal(Dono dono, TipoAnimal tipo){
        return Animal.builder()
                .animalId(this.id)
                .nome(this.nome)
                .dono(dono)
                .tipoAnimal(tipo)
                .build();
    }
}
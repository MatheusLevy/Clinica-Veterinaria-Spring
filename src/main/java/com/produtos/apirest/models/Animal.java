package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.AnimalDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
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

    @Column(name = "nome")
    @NotNull
    private String nome;

    @ManyToOne
    @JoinColumn(name="tipo_id", nullable = false)
    private TipoAnimal tipoAnimal;

    @ManyToOne
    @JoinColumn(name = "dono_id", nullable = false)
    private Dono dono;

    @OneToMany(mappedBy = "animal")
    @JsonIgnore
    private List<Consulta> consultas;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id= "+ animalId +", nome= " + nome +", tipo= " + tipoAnimal + ", dono= " + dono + "]";
    }

    public AnimalDTO toAnimalDTO(){
        return AnimalDTO.builder()
                .id(this.animalId)
                .idTipoAnimal(this.tipoAnimal.getTipoAnimalId())
                .nomeTipoAnimal(this.tipoAnimal.getNome())
                .nomeDono(this.dono.getNome())
                .idDono(this.dono.getDonoId())
                .build();
    }
}
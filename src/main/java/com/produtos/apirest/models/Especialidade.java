package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.EspecialidadeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="expertise")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long expertiseId;

    @Column(name = "nome")
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name="area_id", nullable = false)
    private Area area;

    @OneToMany(mappedBy = "expertise", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Veterinario> veterinarys;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + expertiseId + ", name= " + name + ", area= "+ area.getName() + "]";
    }

    public EspecialidadeDTO toEspecialidadeDTO(){
        return EspecialidadeDTO.builder()
                .id(this.expertiseId)
                .nome(this.name)
                .idArea(this.area.getAreaId())
                .build();
    }
}
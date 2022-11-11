package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.ExpertiseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="expertise")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expertise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long expertiseId;

    @Column(name = "name")
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name="area_id", nullable = false)
    private Area area;

    @OneToMany(mappedBy = "expertise", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Veterinary> veterinaries;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + expertiseId + ", name= " + name + ", area= "+ area.getName() + "]";
    }

    public ExpertiseDTO toExpertiseDTO(){
        return ExpertiseDTO.builder()
                .id(this.expertiseId)
                .name(this.name)
                .areaId(this.area.getAreaId())
                .build();
    }
}
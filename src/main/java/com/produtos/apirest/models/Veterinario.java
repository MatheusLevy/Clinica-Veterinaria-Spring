package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.VeterinarioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "veterinary")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long veterinaryId;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "phone")
    @NotNull
    private String phone;

    @Column(name = "cpf")
    @NotNull
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "expertise_id", nullable = false)
    private Especialidade expertise;

    @OneToMany(mappedBy = "veterinary", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Consulta> appointments;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + veterinaryId + ", name = " + name
        + ", phone= " + phone + ", cpf= " + cpf + "expertise= " + expertise + " ]";
    }

    public VeterinarioDTO toVeterinarioDTO(){
        return VeterinarioDTO.builder()
                .id(this.veterinaryId)
                .nome(this.name)
                .telefone(this.phone)
                .especialidadeId(this.expertise.getExpertiseId())
                .especialidadeNome(this.expertise.getName())
                .build();
    }
}
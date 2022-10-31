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
@Table(name = "veterinario")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long veterinarioId;

    @Column(name = "nome")
    @NotNull
    private String nome;

    @Column(name = "telefone")
    @NotNull
    private String telefone;

    @Column(name = "cpf")
    @NotNull
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "especialidade_id", nullable = false)
    private Especialidade especialidade;

    @OneToMany(mappedBy = "veterinario", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Consulta> consultas;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + veterinarioId + ", nome = " + nome
        + ", telefone= " + telefone + ", cpf= " + cpf + "especialidade= " + especialidade + " ]";
    }

    public VeterinarioDTO toVeterinarioDTO(){
        return VeterinarioDTO.builder()
                .id(this.veterinarioId)
                .nome(this.nome)
                .telefone(this.telefone)
                .especialidadeId(this.especialidade.getEspecialidadeId())
                .especialidadeNome(this.especialidade.getNome())
                .build();
    }
}
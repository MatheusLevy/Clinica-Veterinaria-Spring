package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.DonoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "dono")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dono {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long donoId;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cpf")
    private String cpf;

    @OneToMany(mappedBy = "dono", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Animal> animais;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[donoId=" + donoId + ", nome=" + nome +", telefone=" + telefone + ", cpf= " + cpf + " ]";
    }

    public DonoDTO toDonoDTO(){
       return DonoDTO.builder()
                .id(this.donoId)
                .telefone(this.telefone)
                .nome(this.nome)
                .build();
    }
}
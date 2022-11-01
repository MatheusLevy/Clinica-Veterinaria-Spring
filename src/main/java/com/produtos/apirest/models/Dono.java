package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.DonoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "owner")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dono {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long donoId;

    @Column(name = "nome")
    @NotNull
    private String name;

    @Column(name = "phone")
    @NotNull
    private String phone;

    @Column(name = "cpf")
    @NotNull
    private String cpf;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Animal> animais;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[OwnerId=" + donoId + ", name=" + name +", phone=" + phone + ", cpf= " + cpf + " ]";
    }

    public DonoDTO toDonoDTO(){
       return DonoDTO.builder()
                .id(this.donoId)
                .telefone(this.phone)
                .nome(this.name)
                .build();
    }
}
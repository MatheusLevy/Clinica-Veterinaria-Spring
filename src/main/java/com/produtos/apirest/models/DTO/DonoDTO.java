package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Dono;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonoDTO {

    private Long id;
    private String nome;
    private String telefone;
    private String cpf;
    private List<Animal> animais;
    private List<Dono> donosList;

    public Dono toDono(){
        return Dono.builder()
                .donoId(this.id)
                .nome(this.nome)
                .cpf(this.cpf)
                .telefone(this.telefone)
                .build();
    }
}
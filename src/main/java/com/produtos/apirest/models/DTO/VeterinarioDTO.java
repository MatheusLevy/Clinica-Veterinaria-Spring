package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.models.Veterinario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VeterinarioDTO {
    private Long id;
    private String nome;
    private String telefone;
    private String cpf;
    private Long especialidadeId;
    private String especialidadeNome;
    private Especialidade especialidade;
    private List<Especialidade> especialidades;

    public Veterinario toVeterinario(){
        return Veterinario.builder()
                .veterinaryId(this.id)
                .name(this.nome)
                .cpf(this.cpf)
                .phone(this.telefone)
                .expertise(this.especialidade)
                .build();
    }

    public Veterinario toVeterinario(Especialidade especialidade){
        return Veterinario.builder()
                .veterinaryId(this.id)
                .name(this.nome)
                .cpf(this.cpf)
                .phone(this.telefone)
                .expertise(especialidade)
                .build();
    }
}

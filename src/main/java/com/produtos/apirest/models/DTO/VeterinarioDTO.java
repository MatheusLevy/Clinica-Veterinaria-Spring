package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Especialidade;
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
    private Especialidade especialidade;
    private List<Especialidade> especialidades;

}

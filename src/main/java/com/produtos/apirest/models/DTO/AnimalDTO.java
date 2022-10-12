package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.TipoAnimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO {
    private Long id;
    private String nome;

    private Long idDono;
    private Dono dono;
    private String nomeDono;
    private List<Dono> donos;

    private TipoAnimal tipo;
    private Long idTipoAnimal;
    private String nomeTipoAnimal;
    private List<TipoAnimal> tipos;
}

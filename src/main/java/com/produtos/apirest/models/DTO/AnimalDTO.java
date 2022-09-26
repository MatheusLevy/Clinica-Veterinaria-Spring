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
    private Dono dono;
    private TipoAnimal tipo;
    private Long idTipoAnimal;
    private Long idDono;
    private List<Dono> donos;
    private List<TipoAnimal> tipos;
}

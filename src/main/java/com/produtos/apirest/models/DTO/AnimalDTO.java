package com.produtos.apirest.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO {
    private Long id;
    private String nome;
    private Long idTipoAnimal;
    private Long idDono;
}

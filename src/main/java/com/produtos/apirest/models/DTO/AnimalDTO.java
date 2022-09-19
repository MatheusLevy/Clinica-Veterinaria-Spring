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
    private String nome;
    private Long id_tipo_animal;
    private Long id_dono;
}

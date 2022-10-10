package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Area;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EspecialidadeDTO {

    private Long id;
    private String nome;
    private Long idArea;
    private Area area;
    private List<Area> areas;
}

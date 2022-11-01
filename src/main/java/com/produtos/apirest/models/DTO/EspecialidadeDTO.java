package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Area;
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
public class EspecialidadeDTO {

    private Long id;
    private String nome;
    private Long idArea;
    private Area area;
    private List<Area> areas;

    public Especialidade toEspecialidade(){
        return Especialidade.builder()
                .expertiseId(this.id)
                .area(this.area)
                .name(this.nome)
                .build();
    }

    public Especialidade toEspecialidade(Area area){
        return Especialidade.builder()
                .expertiseId(this.id)
                .area(area)
                .name(this.nome)
                .build();
    }
}
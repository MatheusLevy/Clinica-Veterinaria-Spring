package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Consulta;
import com.produtos.apirest.models.TipoConsulta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoConsultaDTO {

    private Long id;
    private String nome;
    private List<Consulta> consultas;
    private List<TipoConsulta> tipoConsultaList;

    public TipoConsulta toTipoConsulta(){
        return TipoConsulta.builder()
                .appointmentTypeId(this.id)
                .name(this.nome)
                .appointments(this.consultas)
                .build();
    }
}
package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.models.Veterinario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaDTO {

    private Long id;
    private Veterinario veterinario;
    private String veterinarioNome;
    private Long veterinarioId;
    private List<Veterinario> veterinarios;
    private Animal animal;
    private String animalNome;
    private Long idAnimal;
    private List<Animal> animais;
    private String descricao;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;
    private TipoConsulta tipo;
    private String tipoNome;
    private Long tipoConsultaId;
    private List<TipoConsulta> tiposConsulta;
}

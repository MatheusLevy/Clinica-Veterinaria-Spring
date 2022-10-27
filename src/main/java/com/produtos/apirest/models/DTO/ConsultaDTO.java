package com.produtos.apirest.models.DTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Consulta;
import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.models.Veterinario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Convert;
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
    private Long animalId;
    private List<Animal> animais;
    private String descricao;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDate data;
    private TipoConsulta tipo;
    private String tipoNome;
    private Long tipoConsultaId;
    private List<TipoConsulta> tiposConsulta;

    public Consulta toConsulta(){
        return Consulta.builder()
                .consultaId(this.id)
                .data(this.data)
                .descricao(this.descricao)
                .animal(this.animal)
                .veterinario(this.veterinario)
                .tipoConsulta(this.tipo)
                .build();
    }

    public Consulta toConsulta(Animal animal, Veterinario veterinario, TipoConsulta tipoConsulta){
        return Consulta.builder()
                .consultaId(this.id)
                .data(this.data)
                .descricao(this.descricao)
                .animal(animal)
                .veterinario(veterinario)
                .tipoConsulta(tipoConsulta)
                .build();
    }
}

package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Expertise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertiseDTO {

    private Long id;
    private String name;
    private Long areaId;
    private Area area;
    private List<Area> areas;

    public Expertise toExpertise(){
        return Expertise.builder()
                .expertiseId(this.id)
                .area(this.area)
                .name(this.name)
                .build();
    }

    public Expertise toExpertise(Area area){
        return Expertise.builder()
                .expertiseId(this.id)
                .area(area)
                .name(this.name)
                .build();
    }
}
package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Area;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaDTO {

    private Long id;
    private String name;
    private List<Area> areas;

    private void hasId(){
        if (this.id == null){
            this.id = 0L;
        }
    }

    private void hasName(){
        if (this.name == null){
            this.name = "";
        }
    }

    public Area toArea(){
        hasId();
        hasName();
        return Area.builder()
                .areaId(this.id)
                .name(this.name)
                .build();
    }
}
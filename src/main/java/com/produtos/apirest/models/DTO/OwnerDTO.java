package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Owner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDTO {

    private Long id;
    private String name;
    private String phone;
    private String cpf;
    private List<Animal> animals;
    private List<Owner> owners;

    private void hasId(){
        if(this.id == null)
            this.id = 0L;
    }
    public Owner toOwner(){
        return Owner.builder()
                .ownerId(this.id)
                .name(this.name)
                .cpf(this.cpf)
                .phone(this.phone)
                .build();
    }
}
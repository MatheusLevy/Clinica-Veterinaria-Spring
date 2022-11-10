package com.produtos.apirest.models.DTO;

import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.models.Veterinary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VeterinaryDTO {
    private Long id;
    private String name;
    private String phone;
    private String cpf;
    private Long expertiseId;
    private String expertiseName;
    private Expertise expertise;
    private List<Expertise> expertises;

    private void hasId(){
        if(this.id == null)
            this.id = 0L;
    }

    private void hasName(){
        if (this.name == null){
            this.name = "";
        }
    }

    private void hasPhone(){
        if (this.phone == null)
            this.phone = "";
    }

    private void hasCPF(){
        if (this.cpf == null)
            this.cpf = "";
    }

    private void hasAllFieldsNotNull(){
        hasId();
        hasCPF();
        hasName();
        hasPhone();
    }

    public Veterinary toVeterinary(){
        hasAllFieldsNotNull();
        return Veterinary.builder()
                .veterinaryId(this.id)
                .name(this.name)
                .cpf(this.cpf)
                .phone(this.phone)
                .expertise(this.expertise)
                .build();
    }

    public Veterinary toVeterinary(Expertise expertise){
        hasAllFieldsNotNull();
        return Veterinary.builder()
                .veterinaryId(this.id)
                .name(this.name)
                .cpf(this.cpf)
                .phone(this.phone)
                .expertise(expertise)
                .build();
    }
}

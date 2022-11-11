package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.OwnerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Table(name = "owner")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long ownerId;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "phone")
    @NotNull
    private String phone;

    @Column(name = "cpf")
    @NotNull
    private String cpf;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Animal> animals;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[OwnerId=" + ownerId + ", name=" + name +", phone=" + phone + ", cpf= " + cpf + " ]";
    }

    public OwnerDTO toOwnerDTO(){
       return OwnerDTO.builder()
                .id(this.ownerId)
                .phone(this.phone)
                .name(this.name)
                .build();
    }
}
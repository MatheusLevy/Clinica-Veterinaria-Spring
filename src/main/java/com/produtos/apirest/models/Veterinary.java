package com.produtos.apirest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.produtos.apirest.models.DTO.VeterinaryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "veterinary")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veterinary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long veterinaryId;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "phone")
    @NotNull
    private String phone;

    @Column(name = "cpf")
    @NotNull
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "expertise_id", nullable = false)
    private Expertise expertise;

    @OneToMany(mappedBy = "veterinary", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Appointment> appointments;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + veterinaryId + ", name = " + name
        + ", phone= " + phone + ", cpf= " + cpf + "expertise= " + expertise + " ]";
    }

    public VeterinaryDTO toVeterinaryDTO(){
        return VeterinaryDTO.builder()
                .id(this.veterinaryId)
                .name(this.name)
                .phone(this.phone)
                .expertiseId(this.expertise.getExpertiseId())
                .expertiseName(this.expertise.getName())
                .build();
    }
}
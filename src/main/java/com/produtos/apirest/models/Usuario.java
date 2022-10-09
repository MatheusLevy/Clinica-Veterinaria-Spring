package com.produtos.apirest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long usuarioId;

    @Column(name = "usuario")
    private String username;

    @Column(name = "senha")
    private String senha;

    @Column(name = "nivel")
    private String nivel;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + usuarioId + ", username= " + username
                + ", senha= " + senha + ", nivel= " + nivel +" ]";
    }
}

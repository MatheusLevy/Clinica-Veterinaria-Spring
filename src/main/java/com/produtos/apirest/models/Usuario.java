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

    //TODO: **Alterar o Nível para ser uma Lista
    // - [ ] Criar Tabela de Roles no Banco
    // - [ ] Adicionar Entity de Role
    // - [ ] Alterar de String pra List<Role>
    @Column(name = "nivel")
    private String nivel;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[id= " + usuarioId + ", username= " + username
                + ", senha= " + senha + ", nivel= " + nivel +" ]";
    }
}

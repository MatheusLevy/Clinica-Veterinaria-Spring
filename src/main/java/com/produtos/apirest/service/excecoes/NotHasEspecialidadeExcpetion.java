package com.produtos.apirest.service.excecoes;

public class NotHasEspecialidadeExcpetion extends RuntimeException{
    public NotHasEspecialidadeExcpetion(Object objeto){
        super(objeto.getClass() + " deve ter Especialidade");
    }
}

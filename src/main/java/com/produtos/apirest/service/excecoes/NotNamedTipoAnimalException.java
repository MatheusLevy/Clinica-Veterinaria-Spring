package com.produtos.apirest.service.excecoes;

import org.aspectj.weaver.ast.Not;

public class NotNamedTipoAnimalException extends RuntimeException{
    public NotNamedTipoAnimalException(){
        super("O Tipo Animal deve possuir um nome!");
    }
}

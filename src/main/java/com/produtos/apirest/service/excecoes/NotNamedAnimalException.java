package com.produtos.apirest.service.excecoes;

public class NotNamedAnimalException extends RuntimeException{
    public NotNamedAnimalException(){
        super("Animal deve ter um nome!");
    }
}

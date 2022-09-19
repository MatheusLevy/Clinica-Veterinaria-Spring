package com.produtos.apirest.service.excecoes;

public class NullTipo_animalException extends NullPointerException{
    public NullTipo_animalException(){
        super("Tipo Animal não pode ser nulo!");
    }
}

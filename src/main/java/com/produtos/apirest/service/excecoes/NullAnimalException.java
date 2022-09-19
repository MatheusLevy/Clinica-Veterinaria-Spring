package com.produtos.apirest.service.excecoes;

public class NullAnimalException extends NullPointerException{
    public NullAnimalException(){
        super("O Animal não pode ser nulo!");
    }
}

package com.produtos.apirest.service.excecoes;

public class NullAreaException extends NullPointerException{

    public NullAreaException(){
        super("A área não pode ser nula!");
    }
}

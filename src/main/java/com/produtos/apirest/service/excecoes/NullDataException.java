package com.produtos.apirest.service.excecoes;

public class NullDataException extends  NullPointerException{
    public NullDataException(Object objeto){
        super("Data de " + objeto.getClass() + " não pode ser nula!");
    }
}

package com.produtos.apirest.service.excecoes;

public class NotIdentifiableObject extends RuntimeException{
    public NotIdentifiableObject(Object objeto){
        super("O objeto " + objeto.getClass() + " não possui identificador!");
    }
}

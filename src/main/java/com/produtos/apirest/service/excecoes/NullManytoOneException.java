package com.produtos.apirest.service.excecoes;

public class NullManytoOneException extends RuntimeException{
    public NullManytoOneException(Object objeto){
        super(objeto.getClass() + " não pode possuir atributo Many to One nulo!");
    }
}

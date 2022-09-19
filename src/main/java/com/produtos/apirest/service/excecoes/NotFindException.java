package com.produtos.apirest.service.excecoes;

public class NotFindException extends RuntimeException{
    public NotFindException(Object objeto){
        super("Elemento " + objeto.getClass() +  " não encontrado na Base de Dados");
    }
}

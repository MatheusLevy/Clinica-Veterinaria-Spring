package com.produtos.apirest.service.excecoes;

public class NotNamedArea extends RuntimeException{
    public NotNamedArea(){
        super("A área deve possuir um nome!");
    }
}

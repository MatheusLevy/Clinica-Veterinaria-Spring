package com.produtos.apirest.service.excecoes;

public class NotHasCpfException extends RuntimeException{
    public NotHasCpfException(Object objeto){
        super(objeto.getClass() + " deve ter CPF!ss");
    }
}

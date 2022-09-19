package com.produtos.apirest.service.excecoes;

public class NotNamedDonoException extends RuntimeException{
    public NotNamedDonoException(){
        super("Dono deve ter nome!");
    }
}

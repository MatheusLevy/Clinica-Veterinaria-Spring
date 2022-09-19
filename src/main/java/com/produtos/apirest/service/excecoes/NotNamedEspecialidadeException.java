package com.produtos.apirest.service.excecoes;

public class NotNamedEspecialidadeException extends ArrayIndexOutOfBoundsException{
    public NotNamedEspecialidadeException(){
        super("Especidade deve ter um nome!");
    }

}

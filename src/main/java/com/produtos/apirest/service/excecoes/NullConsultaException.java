package com.produtos.apirest.service.excecoes;

public class NullConsultaException extends NullPointerException{
    public NullConsultaException(){
        super("Consulta não pode ser nula!");
    }
}

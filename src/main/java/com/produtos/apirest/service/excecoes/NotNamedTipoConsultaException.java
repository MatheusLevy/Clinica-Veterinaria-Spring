package com.produtos.apirest.service.excecoes;

public class NotNamedTipoConsultaException extends RuntimeException{
    public NotNamedTipoConsultaException(){
        super("Tipo Consulta precisa ter um nome!");
    }
}

package com.produtos.apirest.service.excecoes;

public class NullTipo_ConsultaException extends NullPointerException{
    public NullTipo_ConsultaException(){
        super("Tipo Consulta não pode ser null!");
    }
}

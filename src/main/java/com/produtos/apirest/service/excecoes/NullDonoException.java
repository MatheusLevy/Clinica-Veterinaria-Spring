package com.produtos.apirest.service.excecoes;

public class NullDonoException extends NullPointerException{
    public NullDonoException(){
        super("Dono não pode ser nulo!");
    }
}

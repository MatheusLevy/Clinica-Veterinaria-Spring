package com.produtos.apirest.service.excecoes;

public class NullVeterinarioException extends NullPointerException{
    public NullVeterinarioException(){
        super("Veterinario não pode ser nulo!");
    }
}

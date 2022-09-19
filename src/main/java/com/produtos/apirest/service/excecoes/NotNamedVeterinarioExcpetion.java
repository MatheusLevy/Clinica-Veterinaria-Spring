package com.produtos.apirest.service.excecoes;

public class NotNamedVeterinarioExcpetion extends RuntimeException{
    public NotNamedVeterinarioExcpetion(){
        super("Veterinário deve ter um nome!");
    }
}

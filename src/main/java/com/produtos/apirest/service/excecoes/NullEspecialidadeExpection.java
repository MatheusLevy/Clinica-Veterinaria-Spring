package com.produtos.apirest.service.excecoes;

public class NullEspecialidadeExpection extends NullPointerException{
    public NullEspecialidadeExpection(){
        super("Especialidade não pode ser nula!");
    }
}

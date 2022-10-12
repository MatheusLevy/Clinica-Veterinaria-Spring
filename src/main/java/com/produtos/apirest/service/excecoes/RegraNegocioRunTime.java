package com.produtos.apirest.service.excecoes;
public class RegraNegocioRunTime extends RuntimeException{
    public RegraNegocioRunTime(String msg){
        super(msg);
    }
}

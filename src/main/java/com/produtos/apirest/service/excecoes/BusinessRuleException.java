package com.produtos.apirest.service.excecoes;
public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException(String msg){
        super(msg);
    }
}

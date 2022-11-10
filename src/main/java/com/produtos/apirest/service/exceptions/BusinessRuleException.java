package com.produtos.apirest.service.exceptions;
public class BusinessRuleException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public BusinessRuleException(String msg){
        super(msg);
    }
}

package com.produtos.apirest.service.exceptions;
public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException(String msg){
        super(msg);
    }
}

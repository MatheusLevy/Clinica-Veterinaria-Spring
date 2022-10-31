package com.produtos.apirest.service.excecoes;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationFailedExpection extends AuthenticationException {
    public AuthenticationFailedExpection(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationFailedExpection(String msg) {
        super(msg);
    }
}

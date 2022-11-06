package com.produtos.apirest.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationFailedException extends AuthenticationException {
    public AuthenticationFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationFailedException(String msg) {
        super(msg);
    }
}

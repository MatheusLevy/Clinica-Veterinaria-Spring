package com.produtos.apirest.exceptionHandler;

import com.produtos.apirest.service.exceptions.AuthenticationFailedException;
import com.produtos.apirest.service.exceptions.BusinessRuleException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    private Problem buildProblem(HttpStatus status, String title){
        return Problem.builder()
                .status(status.value())
                .dateTime(LocalDateTime.now())
                .title(title)
                .build();
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMessageNotReadable(@NotNull HttpMessageNotReadableException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        Problem problem = buildProblem(status, "Required request body is missing");
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Object> handleBusinessRuleException(BusinessRuleException ex, WebRequest request){
        HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
        Problem problem = buildProblem(BAD_REQUEST, ex.getMessage());
        return handleExceptionInternal(ex, problem, new HttpHeaders(), BAD_REQUEST, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Object> handleAuthenticationFailedException(AuthenticationFailedException ex, WebRequest request){
        HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;
        Problem problem = buildProblem(UNAUTHORIZED, ex.getMessage());
        return handleExceptionInternal(ex, problem, new HttpHeaders(), UNAUTHORIZED, request);
    }
}
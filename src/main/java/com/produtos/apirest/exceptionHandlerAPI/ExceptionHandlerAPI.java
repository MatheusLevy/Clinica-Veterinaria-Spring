package com.produtos.apirest.exceptionHandlerAPI;

import com.produtos.apirest.service.exceptions.AuthenticationFailedException;
import com.produtos.apirest.service.exceptions.BusinessRuleException;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ControllerAdvice
public class ExceptionHandlerAPI extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    private ErrorTemplate buildProblem(HttpStatus status, String title){
        return ErrorTemplate.builder()
                .status(status.value())
                .dateTime(LocalDateTime.now())
                .title(title)
                .build();
    }

    private List<ErrorTemplate.Field> getInvalidFields(MethodArgumentNotValidException ex){
        List<ErrorTemplate.Field> fields = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()){
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            fields.add(new ErrorTemplate.Field(name, message));
        }
        return fields;
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMessageNotReadable(@NotNull HttpMessageNotReadableException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        ErrorTemplate errorTemplate = buildProblem(status, "Required request body is missing");
        return handleExceptionInternal(ex, errorTemplate, headers, status, request);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Object> handleBusinessRuleException(BusinessRuleException ex, WebRequest request){
        HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
        ErrorTemplate errorTemplate = buildProblem(BAD_REQUEST, ex.getMessage());
        return handleExceptionInternal(ex, errorTemplate, new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Object> handleAuthenticationFailedException(AuthenticationFailedException ex, WebRequest request){
        HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;
        ErrorTemplate errorTemplate = buildProblem(UNAUTHORIZED, ex.getMessage());
        return handleExceptionInternal(ex, errorTemplate, new HttpHeaders(), UNAUTHORIZED, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
        ErrorTemplate errorTemplate = buildProblem(status, "One or more fields invalids!");
        errorTemplate.setFields(getInvalidFields(ex));
        return handleExceptionInternal(ex, errorTemplate, new HttpHeaders(), BAD_REQUEST, request);
    }
}
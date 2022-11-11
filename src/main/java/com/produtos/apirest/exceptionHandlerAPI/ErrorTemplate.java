package com.produtos.apirest.exceptionHandlerAPI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ErrorTemplate {

    private static final long serialVersionUID = 2L;
    private Integer status;
    private LocalDateTime dateTime;
    private String title;
    private List<Field> fields;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Field{
        private String name;
        private String message;
    }
}
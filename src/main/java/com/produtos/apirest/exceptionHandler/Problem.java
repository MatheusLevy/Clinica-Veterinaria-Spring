package com.produtos.apirest.exceptionHandler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Problem {
    private Integer status;
    private LocalDateTime dateTime;
    private String title;
}
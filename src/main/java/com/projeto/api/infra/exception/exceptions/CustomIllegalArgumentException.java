package com.projeto.api.infra.exception.exceptions;

public class CustomIllegalArgumentException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public CustomIllegalArgumentException(String message) {
        super(message);
    }
}

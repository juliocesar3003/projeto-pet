package com.projeto.api.infra.exception.exceptions;

public class CustomACessDeniedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public CustomACessDeniedException(String message) {
        super(message);
    }
}

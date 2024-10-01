package com.projeto.api.infra.exception.exceptions;

public class CustomDeleteporStageError extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public CustomDeleteporStageError(String message) {
        super(message);
    }
}

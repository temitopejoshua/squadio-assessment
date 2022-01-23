package com.squadio.exception;

public class BadRequestException extends RuntimeException {
    private String reason;
    public BadRequestException(String message) {
        super(message);
        this.reason = message;
    }
    @Override
    public String getMessage() {
        return reason;
    }
}

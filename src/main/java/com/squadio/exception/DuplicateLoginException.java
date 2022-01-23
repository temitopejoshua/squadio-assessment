package com.squadio.exception;


public class DuplicateLoginException extends RuntimeException{
    private String reason;
    public DuplicateLoginException(String reason) {
        super(reason);
        this.reason=reason;
    }

    @Override
    public String getMessage() {
        return reason;
    }
}

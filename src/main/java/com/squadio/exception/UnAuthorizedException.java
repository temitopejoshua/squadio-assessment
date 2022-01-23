package com.squadio.exception;

public class UnAuthorizedException extends RuntimeException {
    private String reason;
    public UnAuthorizedException(String reason) {
        super(reason);
        this.reason=reason;
    }

    @Override
    public String getMessage() {
        return reason;
    }
}

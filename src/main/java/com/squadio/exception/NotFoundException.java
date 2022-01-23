package com.squadio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    private String reason;
    public NotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
        this.reason=reason;
    }

    @Override
    public String getMessage() {
        return reason;
    }

}

package com.codesqad.cms.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResourceNotFoundException extends BaseException {
    private static final String timestamp = new java.util.Date().toString();
    public ResourceNotFoundException(HttpStatus status, String error, String message) {
        super(status, error, message, timestamp);
    }
}

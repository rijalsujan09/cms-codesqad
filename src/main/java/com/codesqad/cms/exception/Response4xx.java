package com.codesqad.cms.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@Builder
public class Response4xx {
    private HttpStatus status;
    private String error;
    private String message;
    private String timestamp;
}

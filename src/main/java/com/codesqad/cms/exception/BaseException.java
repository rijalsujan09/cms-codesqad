package com.codesqad.cms.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Getter
@Setter
@ToString
public class BaseException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(BaseException.class);

    private final HttpStatus status;
    private final String error;
    private final String message;
    private final String timestamp;

    @Builder(builderMethodName = "baseExceptionBuilder")
    public BaseException(HttpStatus status, String error, String message, String timestamp) {
        super(message);
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        logException(null);

    }

    @Builder(builderMethodName = "baseExceptionBuilderWithCause")
    public BaseException(HttpStatus status, String error, String message, Throwable e) {
        super(message);
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        logException(e);
    }

    private void logException(Throwable e) {
        logger.error( "Server Error: Status [{}], Error [{}], Message [{}], Timestamp [{}]",
                status.value(), error, message, timestamp);
    }
}

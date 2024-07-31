package com.codesqad.cms.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class Response5xx extends BaseException {
        private Response5xx(String message, Throwable cause) {
            super(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", message, cause);
        }
        public interface Message {
            Cause message(String message);
        }

        public interface Cause {
            BuildStage cause(Throwable cause);
        }

        public interface BuildStage {
            Response5xx build();
        }

        public static class Builder implements Message, Cause, BuildStage {
            private String message;
            private Throwable cause;

            private Builder() {}

            @Override
            public Cause message(String message) {
                this.message = message;
                return this;
            }

            @Override
            public BuildStage cause(Throwable cause) {
                this.cause = cause;
                return this;
            }

            @Override
            public Response5xx build() {
                return new Response5xx(message, cause);
            }
        }

        public static Message builder() {
            return new Builder();
        }
}

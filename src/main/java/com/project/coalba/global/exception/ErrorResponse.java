package com.project.coalba.global.exception;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        status = errorCode.getHttpStatus().value();
        error = errorCode.getHttpStatus().name();
        code = errorCode.name();
        message = errorCode.getMessage();
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode));
    }
}

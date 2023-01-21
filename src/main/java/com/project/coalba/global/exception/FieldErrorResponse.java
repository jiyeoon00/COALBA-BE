package com.project.coalba.global.exception;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class FieldErrorResponse extends ErrorResponse {
    private final List<String> defaultMessages;

    public FieldErrorResponse(ErrorCode errorCode, List<String> defaultMessages) {
        super(errorCode);
        this.defaultMessages = defaultMessages;
    }

    public static ResponseEntity<FieldErrorResponse> toResponseEntity(ErrorCode errorCode, List<String> defaultMessages) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new FieldErrorResponse(errorCode, defaultMessages));
    }
}

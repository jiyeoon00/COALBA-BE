package com.project.coalba.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return ErrorResponse.toResponse(e.getErrorCode());
    }
}

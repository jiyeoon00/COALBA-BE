package com.project.coalba.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
    
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<FieldErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> defaultMessages = new ArrayList<>();
        for (ObjectError error : e.getAllErrors()) {
            defaultMessages.add(error.getDefaultMessage());
        }
        return FieldErrorResponse.toResponseEntity(ErrorCode.INVALID_REQUEST_FIELD, defaultMessages);
    }
}

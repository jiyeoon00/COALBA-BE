package com.project.coalba.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<FieldErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> defaultMessages = new ArrayList<>();
        for (ObjectError error : e.getAllErrors()) {
            defaultMessages.add(error.getDefaultMessage());
        }
        return FieldErrorResponse.toResponseEntity(ErrorCode.INVALID_REQUEST_FIELD, defaultMessages);
    }

    @ExceptionHandler(value = {InvitationFailException.class})
    protected String handleInvitationFailException(InvitationFailException e) {
        log.error(e.getMessage(), e);
        return "invitation-fail.html";
    }
}

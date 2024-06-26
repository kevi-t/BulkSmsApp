package com.project.messageapp.exceptions;

import com.project.messageapp.responses.UniversalResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptions {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public UniversalResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        String errorMessage = String.join(",", errors);
        return UniversalResponse.builder()
                .message(errorMessage)
                .status("1")
                .build();
    }
    @ExceptionHandler(NoValidPhoneNumbersFoundException.class)
    public UniversalResponse handleNoValidPhoneNumbersFoundException(NoValidPhoneNumbersFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", "0");
        response.put("data", null);
        return UniversalResponse.builder()
                .message(ex.getMessage())
                .status("1")
                .data(response)
                .build();
    }

}
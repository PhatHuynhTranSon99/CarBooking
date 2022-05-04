package com.transonphat.carbooking.exceptions.handlers;

import com.transonphat.carbooking.exceptions.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponse> handleGenericExceptions(Exception exception, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomerNotFoundException(BadRequestException exception,
                                                                                WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getParameterName() + " is missing from request",
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //Get the field errors
        List<FieldError> fieldErrorList = ex.getBindingResult().getFieldErrors();

        //If there are any field errors -> Display the first one
        //Else display generic message
        String message = fieldErrorList.size() > 0 ?
                            fieldErrorList.get(0).getDefaultMessage() :
                            ex.getParameter().getParameterName() + "is not valid";

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                message,
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}

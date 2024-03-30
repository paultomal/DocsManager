package com.example.document_management_tool.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public ResponseEntity<String> handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException ex) {
        String errorMessage = "Unsupported Media Type: " + ex.getContentType();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> customMethodArgumentNotValidException(MethodArgumentNotValidException m) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Input Validation Is in Correct!!", m.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> customConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorMessages = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation error", errorMessages.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<?> customEmailAlreadyTakenException(EmailAlreadyTakenException e) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Email Already Taken", e.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNameAlreadyTakenException.class)
    public ResponseEntity<?> customUserNameAlreadyTakenException(UserNameAlreadyTakenException u) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Username is Already Taken", u.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalActionException.class)
    public ResponseEntity<?> customIllegalActionExceptionHandling(IllegalActionException exception) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Illegal Action Detected!!!", exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }


}

package com.paypal.bfs.test.bookingserv.exception;

import com.paypal.bfs.test.bookingserv.dto.APIResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sushil Saha
 *
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e) {
        LOGGER.error("Handling ConstraintViolationException ", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        APIResponse apiResponse = new APIResponse();
        apiResponse.setErrors(errors);
        apiResponse.setStatus("ERROR");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleInternalServerError(Exception e) {
        LOGGER.error("Handling exception ", e);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage("Something went wrong, please try again");
        apiResponse.setStatus("ERROR");
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(Exception e) {
        LOGGER.error("Handling exception ", e);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(e.getMessage());
        apiResponse.setStatus("ERROR");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ApplicationException.class})
    public ResponseEntity handleApplicationException(ApplicationException ex) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(ex.getMessage());
        apiResponse.setStatus("ERROR");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
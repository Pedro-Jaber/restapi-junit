package br.com.pedrojaber.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }

        Collections.sort(errors);
        var errorResponse = new ErrorResponse("Validation Error", errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
}

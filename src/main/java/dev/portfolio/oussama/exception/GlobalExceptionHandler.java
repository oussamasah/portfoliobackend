package dev.portfolio.oussama.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if (ex.getMessage().contains("uk8t1bgd1a1yvcluowqbg22fhx4")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error","The provided data already exists."));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

         .body(Map.of("error","An internal error occurred. Please try again later."));
    }
}
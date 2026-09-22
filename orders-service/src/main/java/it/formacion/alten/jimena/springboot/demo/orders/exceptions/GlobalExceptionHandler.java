package it.formacion.alten.jimena.springboot.demo.orders.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String ORDER_NOT_FOUND = "Order Not Found";
    private static final String INVALID_ORDER_REQUEST = "Invalid Order Request";
    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOrderNotFound(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                TIMESTAMP, LocalDateTime.now(),
                STATUS, HttpStatus.NOT_FOUND.value(),
                ERROR, ORDER_NOT_FOUND,
                MESSAGE, ex.getMessage()
        ));
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidOrder(InvalidOrderException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                TIMESTAMP, LocalDateTime.now(),
                STATUS, HttpStatus.BAD_REQUEST.value(),
                ERROR, INVALID_ORDER_REQUEST,
                MESSAGE, ex.getMessage()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                TIMESTAMP, LocalDateTime.now(),
                STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ERROR, INTERNAL_SERVER_ERROR,
                MESSAGE, ex.getMessage()
        ));
    }
}
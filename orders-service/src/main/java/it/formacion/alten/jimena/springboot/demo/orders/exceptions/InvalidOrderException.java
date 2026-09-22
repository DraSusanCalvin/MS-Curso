package it.formacion.alten.jimena.springboot.demo.orders.exceptions;

public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException(String message) {
        super(message);
    }
}

package it.formacion.alten.jimena.springboot.demo.orders.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}

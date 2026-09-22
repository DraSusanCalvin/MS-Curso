package it.formacion.alten.jimena.springboot.demo.orders.clients.adapters;

public record ProductResponse(String name, String description, double price, Long id) {
}
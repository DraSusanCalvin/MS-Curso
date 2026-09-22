package it.formacion.alten.jimena.springboot.demo.orders.kafka.adapters;

public record OrderEventItem(Long productId, Integer quantity) {
}
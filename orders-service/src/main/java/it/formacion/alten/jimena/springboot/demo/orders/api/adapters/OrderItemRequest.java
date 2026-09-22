package it.formacion.alten.jimena.springboot.demo.orders.api.adapters;

public record OrderItemRequest(Long productId, Long quantity) {
}
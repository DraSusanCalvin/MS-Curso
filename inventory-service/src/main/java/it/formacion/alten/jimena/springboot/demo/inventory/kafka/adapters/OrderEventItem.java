package it.formacion.alten.jimena.springboot.demo.inventory.kafka.adapters;

public record OrderEventItem(Long productId, Integer quantity) {
}
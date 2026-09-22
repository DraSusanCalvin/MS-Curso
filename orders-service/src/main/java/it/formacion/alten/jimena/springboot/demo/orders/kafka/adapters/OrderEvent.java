package it.formacion.alten.jimena.springboot.demo.orders.kafka.adapters;

import java.util.List;

public record OrderEvent(Long id, String status, List<OrderEventItem> items) {
}
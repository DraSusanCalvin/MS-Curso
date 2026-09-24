package it.formacion.alten.jimena.springboot.demo.inventory.kafka.adapters;

import java.util.List;

public record OrderEvent(Long orderId, String status, List<OrderEventItem> items) {
}

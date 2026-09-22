package it.formacion.alten.jimena.springboot.demo.orders.api.adapters;

import java.util.List;

public record OrderResponse(Long id, String status, List<OrderItemResponse> items) {
    public record OrderItemResponse(Long id, Long productId, Long quantity) {
    }
}

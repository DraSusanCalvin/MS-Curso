package it.formacion.alten.jimena.springboot.demo.orders.api.adapters;

import java.util.List;

public record OrderRequest(List<OrderItemRequest> items) { }
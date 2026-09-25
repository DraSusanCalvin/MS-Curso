package it.formacion.alten.jimena.springboot.demo.inventory.service;

import java.util.List;

import it.formacion.alten.jimena.springboot.demo.inventory.api.adapters.InventoryResponse;
import it.formacion.alten.jimena.springboot.demo.inventory.kafka.adapters.OrderEvent;


public interface InventoryService {

    List<InventoryResponse> getInventario();
    InventoryResponse getStock(Long productId);
    void descontarStock(OrderEvent orderEvent);
}

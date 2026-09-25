package it.formacion.alten.jimena.springboot.demo.inventory.service;

import java.util.List;

import it.formacion.alten.jimena.springboot.demo.inventory.api.adapters.InventoryResponse;


public interface InventoryService {

    List<InventoryResponse> getInventario();
    InventoryResponse getStock(Long productId);
}

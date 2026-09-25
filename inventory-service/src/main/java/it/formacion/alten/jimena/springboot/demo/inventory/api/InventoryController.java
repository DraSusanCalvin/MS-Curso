package it.formacion.alten.jimena.springboot.demo.inventory.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import it.formacion.alten.jimena.springboot.demo.inventory.api.adapters.InventoryResponse;
import it.formacion.alten.jimena.springboot.demo.inventory.service.InventoryService;

@RestController
public class InventoryController implements InventoryApi {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public ResponseEntity<List<InventoryResponse>> getInventario() {
        return ResponseEntity.ok(inventoryService.getInventario());
    }

    @Override
    public ResponseEntity<InventoryResponse> getStock(Long productId) {
        return ResponseEntity.ok(inventoryService.getStock(productId));
    }

}

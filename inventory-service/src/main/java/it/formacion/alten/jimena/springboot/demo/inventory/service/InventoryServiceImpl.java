package it.formacion.alten.jimena.springboot.demo.inventory.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.formacion.alten.jimena.springboot.demo.inventory.api.adapters.InventoryResponse;
import it.formacion.alten.jimena.springboot.demo.inventory.entities.Inventory;
import it.formacion.alten.jimena.springboot.demo.inventory.repository.InventoryRepository;

@Service 
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    private InventoryResponse toResponse(Inventory itemInventario) {
        return new InventoryResponse(itemInventario.getProductId(),
                itemInventario.getStock());
    }

    @Override
    public List<InventoryResponse> getInventario() {
        List<InventoryResponse> lista = inventoryRepository.findAll().stream()
                .map((itemInventario) -> toResponse(itemInventario))
                .toList();

        return lista;
    }

    @Override
    public InventoryResponse getStock(Long productId) {
        Inventory itemInventory = inventoryRepository.findById(productId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra el producto " + productId));

        return toResponse(itemInventory);

    }

}

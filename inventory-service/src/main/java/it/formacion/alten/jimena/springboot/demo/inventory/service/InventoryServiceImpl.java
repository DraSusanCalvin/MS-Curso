package it.formacion.alten.jimena.springboot.demo.inventory.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import org.slf4j.Logger;

import it.formacion.alten.jimena.springboot.demo.inventory.api.adapters.InventoryResponse;

import it.formacion.alten.jimena.springboot.demo.inventory.entities.Inventory;
import it.formacion.alten.jimena.springboot.demo.inventory.kafka.adapters.OrderEvent;
import it.formacion.alten.jimena.springboot.demo.inventory.kafka.adapters.OrderEventItem;
import it.formacion.alten.jimena.springboot.demo.inventory.repository.InventoryRepository;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    private InventoryResponse toResponse(Inventory itemInventario) {
        return new InventoryResponse(itemInventario.getProductId(),
                itemInventario.getStock());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getInventario() {
        List<InventoryResponse> lista = inventoryRepository.findAll().stream()
                .map((itemInventario) -> toResponse(itemInventario))
                .toList();

        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getStock(Long productId) {
        Inventory itemInventory = inventoryRepository.findById(productId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra el producto " + productId));

        return toResponse(itemInventory);

    }

    @Transactional
    @Override
    public void descontarStock(OrderEvent orderEvent) {

        // 1a vuelta: ¿Hay ejemplares de todo?
        for (OrderEventItem item : orderEvent.items()) {
            Optional<Inventory> inventory = inventoryRepository.findById(item.productId());

            if (inventory.isEmpty() || inventory.get().getStock() < item.quantity()) {
                logger.warn("Pedido {} rechazado: no hay suficiente cantidad del producto {}",
                        orderEvent.orderId(), item.productId());
                return;
            }
        }

        // 2a vuelta: Restar stock a cada uno
        for (OrderEventItem orderItem : orderEvent.items()) {
            Inventory inventory = inventoryRepository.findById(orderItem.productId()).orElseThrow();

            inventory.setStock(inventory.getStock() - orderItem.quantity());
        }
        logger.info("Pedido {} registrado ", orderEvent.orderId());
    }

}

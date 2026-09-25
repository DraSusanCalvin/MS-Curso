package it.formacion.alten.jimena.springboot.demo.inventory.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import org.springframework.messaging.handler.annotation.Header;
import it.formacion.alten.jimena.springboot.demo.inventory.kafka.adapters.OrderEvent;
import it.formacion.alten.jimena.springboot.demo.inventory.service.InventoryService;

import org.springframework.kafka.support.KafkaHeaders;

@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);
    private final InventoryService inventoryService;

    public OrderEventListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "orders-topic")
    public void onOrderEvent(OrderEvent event,
            @Header(name = KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(name = KafkaHeaders.OFFSET) long offset) {

        if (!"NEW".equals(event.status())) {
            log.info("Evento ignorado: inventory solo reacciona a pedidos nuevos (status={})", event.status());
            return;
        }
        log.info("📥 Pedido recibido: partition={}, offset={}, event={}", partition, offset, event);
        inventoryService.descontarStock(event);
    }
}

package it.formacion.alten.jimena.springboot.demo.inventory.kafka;

import it.formacion.alten.jimena.springboot.demo.inventory.kafka.adapters.OrderEvent;
import it.formacion.alten.jimena.springboot.demo.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    private final InventoryService inventoryService;

    public OrderEventListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "orders-topic")
    public void onOrderEvent(OrderEvent event,
                             @Header(KafkaHeaders.RECEIVED_KEY) String key,
                             @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                             @Header(KafkaHeaders.OFFSET) long offset) {

        log.info("📥 Evento recibido: key={}, partition={}, offset={}, event={}",
                key, partition, offset, event);

        if (!"NEW".equals(event.status())) {
            log.info("Evento ignorado: inventory solo reacciona a pedidos nuevos (status={})", event.status());
            return;
        }

        inventoryService.descontarStock(event);
    }
}

package it.formacion.alten.jimena.springboot.demo.orders.kafka;

import it.formacion.alten.jimena.springboot.demo.orders.kafka.adapters.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderEvent(OrderEvent event) {
        String key = String.valueOf(event.orderId());
        logger.info("📤 Publicando evento: key={}, event={}", key, event);

        kafkaTemplate.send(KafkaTopicConfig.ORDERS_TOPIC, key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        logger.error("❌ No se pudo publicar el evento del pedido {}", key, ex);
                    } else {
                        logger.info("✅ Evento del pedido {} guardado en partición {}, offset {}",
                                key,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}
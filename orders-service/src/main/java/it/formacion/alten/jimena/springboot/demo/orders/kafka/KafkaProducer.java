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

    public void publishOrderEvent(OrderEvent order) {
        logger.info("✅ Publishing order event: {}", order);
        kafkaTemplate.send("orders-topic", order);
    }
}
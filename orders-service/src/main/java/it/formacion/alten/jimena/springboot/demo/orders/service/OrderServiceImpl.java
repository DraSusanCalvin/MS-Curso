package it.formacion.alten.jimena.springboot.demo.orders.service;


import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import it.formacion.alten.jimena.springboot.demo.orders.api.adapters.OrderRequest;
import it.formacion.alten.jimena.springboot.demo.orders.api.adapters.OrderResponse;
import it.formacion.alten.jimena.springboot.demo.orders.api.adapters.UpdateOrderRequest;
import it.formacion.alten.jimena.springboot.demo.orders.clients.ProductClient;
import it.formacion.alten.jimena.springboot.demo.orders.exceptions.InvalidOrderException;
import it.formacion.alten.jimena.springboot.demo.orders.exceptions.OrderNotFoundException;
import it.formacion.alten.jimena.springboot.demo.orders.kafka.KafkaProducer;
import it.formacion.alten.jimena.springboot.demo.orders.kafka.adapters.OrderEvent;
import it.formacion.alten.jimena.springboot.demo.orders.kafka.adapters.OrderEventItem;
import it.formacion.alten.jimena.springboot.demo.orders.model.Order;
import it.formacion.alten.jimena.springboot.demo.orders.model.OrderItem;
import it.formacion.alten.jimena.springboot.demo.orders.model.OrderStatus;
import it.formacion.alten.jimena.springboot.demo.orders.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String ORDER_NOT_FOUND = "Order not found";
    private static final String PRODUCTS_SERVICE = "productsService";
    private static final String PRODUCTS_SERVICE_RETRY = "productsServiceRetry";

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final KafkaProducer kafkaProducer;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductClient productClient, KafkaProducer kafkaProducer) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Page<OrderResponse> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getStatus().name(),
                        order.getItems().stream()
                                .map(item -> new OrderResponse.OrderItemResponse(item.getId(), item.getProductId(), item.getQuantity()))
                                .toList()
                ));
    }

    @Override
    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND));
        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getItems().stream()
                        .map(item -> new OrderResponse.OrderItemResponse(
                                item.getId(),
                                item.getProductId(),
                                item.getQuantity()))
                        .toList()
        );
    }

    @Override
    //@Retry(name = PRODUCTS_SERVICE_RETRY, fallbackMethod = "createOrderFallback")
    //@CircuitBreaker(name = PRODUCTS_SERVICE, fallbackMethod = "createOrderFallback")
    public OrderResponse createOrder(OrderRequest request) {
        // Validate all products exist (Synchronous call to `products-service`)
        request.items().forEach(item -> productClient.getProductById(item.productId()));

        Order order = Order.builder()
                .status(OrderStatus.NEW)
                .items(new ArrayList<>())
                .build();

        Order finalOrder = order;
        List<OrderItem> items = request.items().stream()
                .map(itemRequest -> OrderItem.builder()
                        .order(finalOrder)
                        .productId(itemRequest.productId())
                        .quantity(itemRequest.quantity())
                        .build())
                .toList();

        order.setItems(items);
        order = orderRepository.save(order);

        // Publish Order Event to Kafka (Async)
        kafkaProducer.publishOrderEvent(new OrderEvent(
                order.getId(),
                order.getStatus().name(),
                order.getItems().stream()
                        .map(item -> new OrderEventItem(
                                item.getProductId(),
                                Math.toIntExact(item.getQuantity())))
                        .toList()
        ));

        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getItems().stream()
                        .map(item -> new OrderResponse.OrderItemResponse(item.getId(), item.getProductId(), item.getQuantity()))
                        .toList()
        );
    }

    public OrderResponse createOrderFallback(OrderRequest request, Throwable ex) {
        return new OrderResponse(1L, "FALLBACK", null);
    }

    @Transactional
    @Override
    public OrderResponse updateOrder(Long id, UpdateOrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND));

        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new InvalidOrderException("Cannot edit a canceled order");
        }

        // Validate all updated products exist
        request.items().forEach(item -> productClient.getProductById(item.productId()));

        order.getItems().clear();
        Order finalOrder = order;
        List<OrderItem> updatedItems = request.items().stream()
                .map(itemRequest -> OrderItem.builder()
                        .order(finalOrder)
                        .productId(itemRequest.productId())
                        .quantity(itemRequest.quantity())
                        .build())
                .toList();

        order.setItems(updatedItems);
        order = orderRepository.save(order);

        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getItems().stream()
                        .map(item -> new OrderResponse.OrderItemResponse(item.getId(), item.getProductId(), item.getQuantity()))
                        .toList()
        );
    }

    @Transactional
    @Override
    public OrderResponse cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND));

        order.setStatus(OrderStatus.CANCELED);
        order = orderRepository.save(order);

        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getItems().stream()
                        .map(item -> new OrderResponse.OrderItemResponse(item.getId(), item.getProductId(), item.getQuantity()))
                        .toList()
        );
    }
}

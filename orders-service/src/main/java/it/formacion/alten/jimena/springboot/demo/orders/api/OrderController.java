package it.formacion.alten.jimena.springboot.demo.orders.api;

import it.formacion.alten.jimena.springboot.demo.orders.api.adapters.OrderRequest;
import it.formacion.alten.jimena.springboot.demo.orders.api.adapters.OrderResponse;
import it.formacion.alten.jimena.springboot.demo.orders.api.adapters.UpdateOrderRequest;
import it.formacion.alten.jimena.springboot.demo.orders.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements OrderApi{

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<Page<OrderResponse>> getOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrders(pageable));
    }

    @Override
    public ResponseEntity<OrderResponse> getOrder(Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @Override
    public ResponseEntity<OrderResponse> createOrder(OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @Override
    public ResponseEntity<OrderResponse> updateOrder(Long id, UpdateOrderRequest request) {
        return ResponseEntity.ok(orderService.updateOrder(id, request));
    }

    @Override
    public ResponseEntity<OrderResponse> cancelOrder(Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
}

package it.formacion.alten.jimena.springboot.demo.orders.service;

import it.formacion.alten.jimena.springboot.demo.orders.api.adapters.OrderRequest;
import it.formacion.alten.jimena.springboot.demo.orders.api.adapters.OrderResponse;
import it.formacion.alten.jimena.springboot.demo.orders.api.adapters.UpdateOrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface OrderService {


    Page<OrderResponse> getOrders(Pageable pageable);

    OrderResponse getOrder(Long id);

    @Transactional
    OrderResponse createOrder(OrderRequest request);

    @Transactional
    OrderResponse updateOrder(Long id, UpdateOrderRequest request);

    @Transactional
    OrderResponse cancelOrder(Long id);
}
package it.formacion.alten.jimena.springboot.demo.orders.repository;

import it.formacion.alten.jimena.springboot.demo.orders.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

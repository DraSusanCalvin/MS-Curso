package it.formacion.alten.jimena.springboot.demo.orders.repository;

import it.formacion.alten.jimena.springboot.demo.orders.model.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAll(Pageable pageable);
}
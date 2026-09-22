package it.formacion.alten.jimena.springboot.demo.products.repository;

import it.formacion.alten.jimena.springboot.demo.products.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
}

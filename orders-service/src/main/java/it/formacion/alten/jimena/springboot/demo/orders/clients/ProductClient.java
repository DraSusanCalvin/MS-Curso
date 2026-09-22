package it.formacion.alten.jimena.springboot.demo.orders.clients;

import it.formacion.alten.jimena.springboot.demo.orders.clients.adapters.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products-service", url = "${products.service.url}")
public interface ProductClient {

    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable("id") Long id);
}
package it.formacion.alten.jimena.springboot.demo.products.service;


import it.formacion.alten.jimena.springboot.demo.products.adapter.ProductRequest;
import it.formacion.alten.jimena.springboot.demo.products.adapter.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse getProduct(Long id);
    Page<ProductResponse> getProducts(Pageable pageable);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    void deleteProduct(Long id);
}

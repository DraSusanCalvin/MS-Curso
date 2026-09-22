package it.formacion.alten.jimena.springboot.demo.products.controller;


import it.formacion.alten.jimena.springboot.demo.products.adapter.ProductRequest;
import it.formacion.alten.jimena.springboot.demo.products.adapter.ProductResponse;
import it.formacion.alten.jimena.springboot.demo.products.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ProductController implements ProductApi {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<Page<ProductResponse>> getProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getProducts(pageable));
    }

    @Override
    public ResponseEntity<ProductResponse> getProduct(Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @Override
    public ResponseEntity<ProductResponse> createProduct(ProductRequest product) {
        System.out.println("Producto recibido: " + product);
        ProductResponse created = productService.createProduct(product);
        System.out.println("Producto creado: " + created);
        return ResponseEntity.created(URI.create("/api/v1/products/" + created.id()))
                .body(created);
    }

    @Override
    public ResponseEntity<ProductResponse> updateProduct(Long id, ProductRequest product) {
        ProductResponse updated = productService.updateProduct(id, product);
        return ResponseEntity.ok(new ProductResponse(
                updated.name(),
                updated.description(),
                updated.price(),
                updated.id()
        ));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ProductResponse> patchProduct(Long id, ProductRequest product) {
        ProductResponse updated = productService.updateProduct(id, product);
        return ResponseEntity.ok(new ProductResponse(
                updated.name(),
                updated.description(),
                updated.price(),
                updated.id()
        ));
    }
}
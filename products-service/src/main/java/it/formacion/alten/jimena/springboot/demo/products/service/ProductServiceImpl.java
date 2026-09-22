package it.formacion.alten.jimena.springboot.demo.products.service;

import it.formacion.alten.jimena.springboot.demo.products.adapter.ProductRequest;
import it.formacion.alten.jimena.springboot.demo.products.adapter.ProductResponse;
import it.formacion.alten.jimena.springboot.demo.products.entities.Product;
import it.formacion.alten.jimena.springboot.demo.products.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        Product savedProduct = productRepository.save(product);
        return new ProductResponse(
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getId());
    }

    @Override
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductResponse(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                id
        );
    }

    @Override
    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(product -> new ProductResponse(
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getId()
                ));
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        Product savedProduct = productRepository.save(product);
        return new ProductResponse(
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getId());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

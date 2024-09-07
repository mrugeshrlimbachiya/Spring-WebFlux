package com.pagination.demo.service;

import com.pagination.demo.entity.Product;
import com.pagination.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Product> searchProducts(String searchTerm, int page, int size) {
        int offset = page * size;
        return productRepository.findByNameContaining("%" + searchTerm + "%", offset, size);
    }

    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(product);
    }
}
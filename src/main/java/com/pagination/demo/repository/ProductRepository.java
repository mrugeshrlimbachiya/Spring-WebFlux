package com.pagination.demo.repository;

import com.pagination.demo.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

    @Query("SELECT * FROM products WHERE name ILIKE :searchTerm OFFSET :offset LIMIT :limit")
    Flux<Product> findByNameContaining(String searchTerm, int offset, int limit);
}
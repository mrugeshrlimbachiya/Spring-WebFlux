package com.pagination.demo.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pagination.demo.entity.Product;
import com.pagination.demo.repository.ProductRepository;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {ProductService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProductServiceDiffblueTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    /**
     * Method under test: {@link ProductService#searchProducts(String, int, int)}
     */
    @Test
    void testSearchProducts() throws AssertionError {
        // Arrange
        Flux<Product> fromIterableResult = Flux.fromIterable(new ArrayList<>());
        when(productRepository.findByNameContaining(Mockito.<String>any(), anyInt(), anyInt()))
                .thenReturn(fromIterableResult);

        // Act
        Flux<Product> actualPublisher = productService.searchProducts("Search Term", 1, 3);

        // Assert
        StepVerifier.FirstStep<Product> createResult = StepVerifier.create(actualPublisher);
        createResult.expectComplete().verify();
        verify(productRepository).findByNameContaining(eq("%Search Term%"), eq(3), eq(3));
        assertSame(fromIterableResult, actualPublisher);
    }

    /**
     * Method under test: {@link ProductService#saveProduct(Product)}
     */
    @Test
    void testSaveProduct() {
        // Arrange
        Mono<Product> justResult = Mono.just(new Product());
        when(productRepository.save(Mockito.<Product>any())).thenReturn(justResult);

        Product product = new Product();
        product.setDescription("The characteristics of someone or something");
        product.setId(1L);
        product.setName("Name");
        product.setPrice(10.0d);

        // Act
        Mono<Product> actualSaveProductResult = productService.saveProduct(product);

        // Assert
        verify(productRepository).save(isA(Product.class));
        assertSame(justResult, actualSaveProductResult);
    }
}

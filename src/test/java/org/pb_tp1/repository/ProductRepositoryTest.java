package org.pb_tp1.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pb_tp1.model.Product;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository = new ProductRepository();
        productRepository.create("Colher", "Colher de inox", 100, 10);
    }

    @Test
    void createTest() {
        Product created = productRepository.create("Faca", "Faca de cortar pão", 199, 5);
        assertNotNull(created);

        Product newProduct = productRepository.getProductById(2);
        assertNotNull(newProduct);
        assertEquals("Faca", newProduct.getNome());
        assertEquals("Faca de cortar pão", newProduct.getDescricao());
        assertEquals(199, newProduct.getPreco());
        assertEquals(5, newProduct.getEstoque());
    }

    @Test
    void removeTest() {
        Product removed = productRepository.delete(1);
        assertNotNull(removed);
        assertEquals("Colher", removed.getNome());
        assertNull(productRepository.getProductById(1));
    }

    @Test
    void updateTest() {
        Product updated = productRepository.update(1, "Colher de Madeira", "Colher rústica", 150, 20);
        assertNotNull(updated);

        Product updatedProduct = productRepository.getProductById(1);
        assertNotNull(updatedProduct);
        assertEquals("Colher de Madeira", updatedProduct.getNome());
        assertEquals("Colher rústica", updatedProduct.getDescricao());
        assertEquals(150, updatedProduct.getPreco());
        assertEquals(20, updatedProduct.getEstoque());
    }

    @Test
    void getByIdTest() {
        Product product = productRepository.getProductById(1);
        assertNotNull(product);
        assertEquals("Colher", product.getNome());
        assertEquals("Colher de inox", product.getDescricao());
        assertEquals(100, product.getPreco());
        assertEquals(10, product.getEstoque());
    }
}

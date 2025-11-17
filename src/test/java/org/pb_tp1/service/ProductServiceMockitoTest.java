package org.pb_tp1.service;

import org.pb_tp1.model.Product;
import org.pb_tp1.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceMockitoTest {
    private ProductRepository repository;
    private ProductService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(ProductRepository.class);
        service = new ProductService(repository);
    }

    @Test
    void testCreateProduct_nomeInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createProduct("", "desc válida", 10.0, 5));
    }

    @Test
    void testCreateProduct_precoNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createProduct("Nome", "desc válida", -1.0, 5));
    }

    @Test
    void testCreateProduct_estoqueNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createProduct("Nome", "desc válida", 10.0, -1));
    }

    @Test
    void testCreateProduct_falhaNoRepositorio() {
        when(repository.create(any(), any(), anyDouble(), anyInt()))
                .thenThrow(new RuntimeException("Timeout no DB"));

        assertThrows(RuntimeException.class,
                () -> service.createProduct("Mouse", "Ótimo", 50.0, 2));

        verify(repository).create("Mouse", "Ótimo", 50.0, 2);
    }

    @Test
    void testUpdateProduct_falhaInternaRepositorio() {
        when(repository.update(anyInt(), anyString(), anyString(), anyDouble(), anyInt()))
                .thenThrow(new RuntimeException("Erro de conexão"));

        assertThrows(RuntimeException.class,
                () -> service.updateProduct(1, "PC", "desc", 150, 10));
    }

    @Test
    void testDeleteProduct_falhaInternaRepositorio() {
        when(repository.delete(anyInt())).thenThrow(new RuntimeException("DB DOWN"));

        assertThrows(RuntimeException.class, () -> service.deleteProduct(1));
    }

    @Test
    void testGetById_produtoNaoExiste() {
        when(repository.getProductById(99)).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> service.getProductById(99));
    }

    @Test
    void testDeleteProduct_produtoNaoExiste() {
        when(repository.delete(123)).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> service.deleteProduct(123));
    }

    @Test
    void testUpdateProduct_produtoNaoExiste() {
        when(repository.update(anyInt(), any(), any(), anyDouble(), anyInt()))
                .thenReturn(null);

        assertThrows(NullPointerException.class,
                () -> service.updateProduct(10, "A", "B", 10, 1));
    }

    @Test
    void testGetAllProducts_listaNula() {
        when(repository.getAllProducts()).thenReturn(null);

        var result = service.getAllProducts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllProducts_falhaInterna() {
        when(repository.getAllProducts()).thenThrow(new RuntimeException("Erro inesperado"));

        assertThrows(RuntimeException.class, () -> service.getAllProducts());
    }
}

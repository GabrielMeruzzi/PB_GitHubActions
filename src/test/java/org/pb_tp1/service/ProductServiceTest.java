package org.pb_tp1.service;

import net.jqwik.api.*;
import net.jqwik.api.lifecycle.BeforeTry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pb_tp1.model.Product;
import org.pb_tp1.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeTry
    @BeforeEach
    void setup() {
        productRepository = new ProductRepository();
        productService = new ProductService(productRepository);
    }

    @Provide
    Arbitrary<String> nome() {
        return Arbitraries.strings().withCharRange('a', 'z').ofMinLength(3).ofMaxLength(20);
    }

    @Provide
    Arbitrary<String> descricao() {
        return Arbitraries.strings().withCharRange('a', 'z').ofMinLength(5).ofMaxLength(50);
    }

    @Provide
    Arbitrary<Double> preco() {
        return Arbitraries.doubles().greaterOrEqual(0.01).lessOrEqual(10000.0);
    }

    @Provide
    Arbitrary<Integer> estoque() {
        return Arbitraries.integers().greaterOrEqual(0).lessOrEqual(1000);
    }

    @Property
    void createRandomProductTest(@ForAll("nome") String nome,
                                 @ForAll("descricao") String descricao,
                                 @ForAll("preco") double preco,
                                 @ForAll("estoque") int estoque) {
        Product product = productService.createProduct(nome, descricao, preco, estoque);
        assertNotNull(product, "O produto criado não deve ser nulo");
        assertFalse(productService.getAllProducts().isEmpty(), "A lista de produtos não deve estar vazia após criar");
    }

    @Test
    @DisplayName("Deve criar e deletar produto corretamente")
    void createThenDeleteTest() {
        Product created = productService.createProduct("Colher", "Colher de inox", 100, 10);
        assertNotNull(created);
        assertEquals(1, productService.getAllProducts().size());

        Product found = productService.getProductById(created.getId());
        assertNotNull(found);

        Product deleted = productService.deleteProduct(created.getId());
        assertNotNull(deleted, "O produto deletado deve ser retornado");
        assertEquals(0, productService.getAllProducts().size());
    }

    @Test
    @DisplayName("Deve criar e atualizar produto corretamente")
    void createThenUpdateTest() {
        Product created = productService.createProduct("Faca", "Faca de pão", 200, 5);
        assertNotNull(created);

        Product updatedProduct = productService.updateProduct(created.getId(), "Faca Nova", "Faca de carne", 250, 15);
        assertNotNull(updatedProduct);
        assertEquals("Faca Nova", updatedProduct.getNome());
        assertEquals("Faca de carne", updatedProduct.getDescricao());
        assertEquals(250, updatedProduct.getPreco());
        assertEquals(15, updatedProduct.getEstoque());
    }

    @Test
    @DisplayName("Erro ao deletar produto inexistente")
    void deleteNonExistentProduct() {
        Product deleted = productService.deleteProduct(999);
        assertNull(deleted, "Deletar um produto inexistente deve retornar null");
    }

    @Test
    @DisplayName("Erro ao buscar produto com ID inválido")
    void invalidIdOnGetProduct() {
        assertNull(productService.getProductById(0));
        assertNull(productService.getProductById(-5));
    }

    @Test
    @DisplayName("Erro ao atualizar produto inexistente")
    void updateNonExistentProduct() {
        Product updatedProduct = productService.updateProduct(42, "Teste", "Teste desc", 10.0, 1);
        assertNull(updatedProduct, "Atualizar produto inexistente deve retornar false");
    }
}

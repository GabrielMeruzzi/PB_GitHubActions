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

    @Provide
    Arbitrary<String> specialChars() {
        return Arbitraries.strings().withChars("!@#$%^&*()<>?/\\|{}[]~").ofMinLength(1).ofMaxLength(10);
    }

    @Property
    void createRandomProductTest(@ForAll("nome") String nome,
                                 @ForAll("descricao") String descricao,
                                 @ForAll("preco") double preco,
                                 @ForAll("estoque") int estoque) {
        assertNotNull(productService.createProduct(nome, descricao, preco, estoque));
        assertFalse(productService.getAllProducts().isEmpty());
    }

    @Property
    void fuzzInvalidStrings(@ForAll String randomText, @ForAll Double preco, @ForAll Integer estoque) {
        Assume.that(randomText != null);
        Assume.that(preco != null && preco > 0);
        Assume.that(estoque != null && estoque >= 0);

        if (randomText.isBlank() || randomText.length() < 3) {
            assertThrows(Exception.class, () -> productService.createProduct(randomText, randomText, preco, estoque));
        }
    }

    @Property
    void fuzzSpecialCharacters(@ForAll("preco") double preco,
                               @ForAll("estoque") int estoque,
                               @ForAll("descricao") String desc,
                               @ForAll("nome") String nome,
                               @ForAll("specialChars") String special) {
        if (special.length() < 3) {
            assertThrows(Exception.class, () -> productService.createProduct(special, desc, preco, estoque));
        }
    }

    @Property
    void fuzzExtremeNumbers(@ForAll Integer estoque) {
        Assume.that(estoque != null);
        if (estoque < 0) {
            assertThrows(Exception.class, () -> productService.createProduct("abc", "abcde", 10.0, estoque));
        }
    }

    @Test
    @DisplayName("Deve criar e deletar produto corretamente")
    void createThenDeleteTest() {
        productService.createProduct("Colher", "Colher de inox", 100, 10);
        assertEquals(1, productService.getAllProducts().size());
        Product p = productService.getProductById(1);
        assertNotNull(p);
        assertNotNull(productService.deleteProduct(1));
        assertEquals(0, productService.getAllProducts().size());
    }

    @Test
    @DisplayName("Deve criar e atualizar produto corretamente")
    void createThenUpdateTest() {
        productService.createProduct("Faca", "Faca de pão", 200, 5);
        assertNotNull(productService.updateProduct(1, "Faca Nova", "Faca de carne", 250, 15));
        Product updated = productService.getProductById(1);
        assertEquals("Faca Nova", updated.getNome());
        assertEquals("Faca de carne", updated.getDescricao());
        assertEquals(250, updated.getPreco());
        assertEquals(15, updated.getEstoque());
    }

    @Test
    @DisplayName("Erro ao deletar produto inexistente")
    void deleteNonExistentProduct() {
        assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(99));
    }

    @Test
    @DisplayName("Erro ao buscar produto com ID inválido")
    void invalidIdOnGetProduct() {
        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(0));
        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(-5));
    }

    @Test
    @DisplayName("Erro ao atualizar produto inexistente")
    void updateNonExistentProduct() {
        assertThrows(NullPointerException.class, () ->
                productService.updateProduct(42, "Teste", "Teste desc", 10.0, 1)
        );
    }
}

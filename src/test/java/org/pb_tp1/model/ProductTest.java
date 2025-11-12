package org.pb_tp1.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("Deve criar produto válido com todos os atributos corretos")
    void createValidProduct() {
        Product p = new Product(1, "Notebook", "Notebook Gamer", 3500.00, 10);

        assertEquals(1, p.getId());
        assertEquals("Notebook", p.getNome());
        assertEquals("Notebook Gamer", p.getDescricao());
        assertEquals(3500.00, p.getPreco());
        assertEquals(10, p.getEstoque());
    }

    @Test
    @DisplayName("Erro ao criar produto com ID menor ou igual a 0")
    void idMenorOuIgualZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(0, "Mouse", "Mouse sem fio", 100.00, 5));

        assertThrows(IllegalArgumentException.class, () ->
                new Product(-1, "Mouse", "Mouse sem fio", 100.00, 5));
    }

    @Test
    @DisplayName("Erro ao criar produto com nome nulo ou menor que 3 caracteres")
    void nomeNuloOuMenorQue3() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(1, null, "Produto válido", 50.00, 2));

        assertThrows(IllegalArgumentException.class, () ->
                new Product(1, "AB", "Produto válido", 50.00, 2));
    }

    @Test
    @DisplayName("Erro ao criar produto com descrição nula ou menor que 5 caracteres")
    void descricaoNulaOuMenorQue5() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(1, "Mesa", null, 500.00, 1));

        assertThrows(IllegalArgumentException.class, () ->
                new Product(1, "Mesa", "1234", 500.00, 1));
    }

    @Test
    @DisplayName("Erro ao criar produto com preço menor ou igual a zero")
    void precoMenorOuIgualZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(1, "Cadeira", "Cadeira Gamer", 0.0, 3));

        assertThrows(IllegalArgumentException.class, () ->
                new Product(1, "Cadeira", "Cadeira Gamer", -10.0, 3));
    }

    @Test
    @DisplayName("Erro ao criar produto com estoque negativo")
    void estoqueNegativo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(1, "Monitor", "Monitor FullHD", 1000.00, -5));
    }

    @Test
    @DisplayName("toString deve retornar string formatada corretamente")
    void toStringValido() {
        Product p = new Product(1, "Teclado", "Teclado Mecânico", 250.00, 15);
        String expected = "Produto ID: 1\n" +
                "  Nome     : Teclado\n" +
                "  Descrição: Teclado Mecânico\n" +
                "  Preço    : 250.0\n" +
                "  Estoque  : 15";

        assertEquals(expected, p.toString());
    }
}

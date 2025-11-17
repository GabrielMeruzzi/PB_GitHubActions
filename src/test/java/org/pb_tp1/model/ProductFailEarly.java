package org.pb_tp1.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductFailEarly {
    @Test
    void shouldThrowExceptionWhenNameInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(1, "a", "Descrição válida", 10.0, 1));
    }

    @Test
    void shouldThrowExceptionWhenDescriptionInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(1, "Nome válido", "abc", 10.0, 1));
    }

    @Test
    void shouldThrowExceptionWhenPriceInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(1, "Nome válido", "Descrição válida", -1.0, 1));
    }

    @Test
    void shouldThrowExceptionWhenStockNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(1, "Nome válido", "Descrição válida", 10.0, -5));
    }
}


package org.pb_tp1.controller;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pb_tp1.App;
import org.pb_tp1.service.ProductService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductControllerTest {
    @Test
    void FailGracefully(){
        ProductService mockService = mock(ProductService.class);
        when(mockService.getAllProducts()).thenThrow(new RuntimeException("DB ERRO"));

        Javalin app = Javalin.create();
        new ProductController(app, mockService);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/products");
            assertEquals(500, response.code());
        });
    }
}
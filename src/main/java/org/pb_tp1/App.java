package org.pb_tp1;

import io.javalin.Javalin;
import org.pb_tp1.controller.ProductController;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
        }).start(7000);
        new ProductController(app);
    }
}
package org.pb_tp1.repository;

import org.pb_tp1.model.Product;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductRepository {
    private final List<Product> productList = new ArrayList<>();
    private int nextId = 1;

    public Product create(String nome, String descricao, double preco, int estoque) {
        Product newProduct = new Product(nextId++, nome, descricao, preco, estoque);
        productList.add(newProduct);
        return newProduct;
    }

    public Product delete(int id) {
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            if (product.getId() == id) {
                productList.remove(i);
                return product;
            }
        }
        return null;
    }

    public Product update(int id, String nome, String descricao, double preco, int estoque) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId() == id) {
                Product updatedProduct = new Product(id, nome, descricao, preco, estoque);
                productList.set(i, updatedProduct);
                return productList.get(i);
            }
        }
        return null;
    }

    public Product getProductById(int id) {
        return productList.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }


    public List<Product> getAllProducts() {
        return new ArrayList<>(productList);
    }
}

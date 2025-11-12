package org.pb_tp1.service;

import lombok.Getter;
import org.pb_tp1.model.Product;
import org.pb_tp1.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductService {
    private final ProductRepository repositorio;

    public ProductService(ProductRepository repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException("O repositório não pode ser nulo.");
        }
        this.repositorio = repositorio;
    }

    public Product createProduct(String nome, String descricao, double preco, int estoque) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio.");
        if (descricao == null || descricao.isBlank())
            throw new IllegalArgumentException("Descrição não pode ser vazia.");
        if (preco < 0) throw new IllegalArgumentException("Preço deve ser maior que zero.");
        if (estoque < 0) throw new IllegalArgumentException("Estoque não pode ser negativo.");

        Product product = repositorio.create(nome, descricao, preco, estoque);
        if (product == null) throw new RuntimeException("Falha ao criar produto.");
        return product;
    }

    public Product deleteProduct(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID deve ser maior que 0.");
        Product deleted = repositorio.delete(id);
        if (deleted == null) throw new IllegalArgumentException("Produto não encontrado para exclusão.");
        return deleted;
    }

    public Product updateProduct(int id, String nome, String descricao, double preco, int estoque) {
        if (id <= 0) throw new IllegalArgumentException("ID deve ser maior que 0.");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio.");
        if (descricao == null || descricao.isBlank())
            throw new IllegalArgumentException("Descrição não pode ser vazia.");
        if (preco <= 0) throw new IllegalArgumentException("Preço deve ser maior que zero.");
        if (estoque < 0) throw new IllegalArgumentException("Estoque não pode ser negativo.");

        Product updatedProduct = repositorio.update(id, nome, descricao, preco, estoque);
        if (updatedProduct == null) throw new NullPointerException("Produto não encontrado para atualização.");

        return repositorio.getProductById(id);
    }

    public List<Product> getAllProducts() {
        List<Product> produtos = repositorio.getAllProducts();
        return (produtos != null) ? produtos : new ArrayList<>();
    }

    public Product getProductById(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID deve ser maior que 0.");
        Product product = repositorio.getProductById(id);
        if (product == null) throw new IllegalArgumentException("Produto não encontrado.");
        return product;
    }
}

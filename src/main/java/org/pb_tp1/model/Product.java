package org.pb_tp1.model;

import lombok.Getter;

@Getter
public class Product {
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;

    public Product(int id, String nome, String descricao, double preco, int estoque) {
        if (id < 0) throw new IllegalArgumentException("ID deve ser maior que 0.");
        if (nome == null || nome.length() < 3)
            throw new IllegalArgumentException("Nome NULL ou menor que 3 caracteres.");
        if (descricao == null || descricao.length() < 5)
            throw new IllegalArgumentException("Descrição NULL ou menor que 5 caracteres.");
        if (preco <= 0)
            throw new IllegalArgumentException("Preço deve ser maior que 0.");
        if (estoque < 0)
            throw new IllegalArgumentException("Estoque não pode ser negativo.");
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return "Produto ID: " + id + "\n" +
                "  Nome     : " + nome + "\n" +
                "  Descrição: " + descricao + "\n" +
                "  Preço    : " + preco + "\n" +
                "  Estoque  : " + estoque;
    }
}

package org.pb_tp1.view;

import org.pb_tp1.model.Product;

import java.util.List;
import java.util.Map;

public class ProductView {
    public static String renderList(List<Product> produtos, String message, String messageType) {
        StringBuilder html = new StringBuilder("""
        <!DOCTYPE html>
        <html lang="pt">
        <head>
            <meta charset="UTF-8">
            <title>Lista de Produtos</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>
        <body class="container mt-5">
            <h1>Lista de Produtos</h1>
            <a href="/products/new" class="btn btn-primary mb-3">Adicionar Novo Produto</a>
        """);

        if (message != null && !message.isEmpty()) {
            if (messageType == null || messageType.isEmpty()) messageType = "info";
            html.append(String.format("""
            <div class="alert alert-%s" role="alert">
                %s
            </div>
            """, messageType, message));
        }

        html.append("""
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Descrição</th>
                                <th>Preço</th>
                                <th>Estoque</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                """);

        for (Product product : produtos) {
            html.append(String.format("""
                            <tr>
                                <td>%d</td>
                                <td>%s</td>
                                <td>%s</td>
                                <td>R$ %.2f</td>
                                <td>%d</td>
                                <td>
                                    <a href="/products/edit/%d" class="btn btn-sm btn-warning">Editar</a>
                                    <form action="/products/delete/%d" method="post" style="display:inline;">
                                        <button type="submit" class="btn btn-sm btn-danger">Deletar</button>
                                    </form>
                                </td>
                            </tr>
                            """, product.getId(), product.getNome(), product.getDescricao(),
                    product.getPreco(), product.getEstoque(), product.getId(), product.getId()));
        }

        html.append("""
                        </tbody>
                    </table>
                </body>
                </html>
                """);
        return html.toString();
    }

    public static String renderForm(Map<String, Object> model) {
        Object id = model.get("id");
        String action = id != null ? "/products/edit/" + id : "/products";
        String title = id != null ? "Editar Produto" : "Novo Produto";

        String nome = (String) model.getOrDefault("nome", "");
        String descricao = (String) model.getOrDefault("descricao", "");
        double preco = (double) model.getOrDefault("preco", 0.0);
        int estoque = (int) model.getOrDefault("estoque", 0);

        String message = (String) model.getOrDefault("message", "");
        String messageType = (String) model.getOrDefault("messageType", "info");
        String alertHtml = "";
        if (!message.isEmpty()) {
            alertHtml = String.format("""
                    <div class="alert alert-%s" role="alert">
                        %s
                    </div>
                    """, messageType, message);
        }

        return String.format("""
                <!DOCTYPE html>
                <html lang="pt">
                <head>
                    <meta charset="UTF-8">
                    <title>%s</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                </head>
                <body class="container mt-5">
                    <h1>%s</h1>
                    %s
                    <form action="%s" method="post">
                        <div class="mb-3">
                            <label for="nome" class="form-label">Nome</label>
                            <input type="text" class="form-control" id="nome" name="nome" value="%s" required>
                        </div>
                        <div class="mb-3">
                            <label for="descricao" class="form-label">Descrição</label>
                            <input type="text" class="form-control" id="descricao" name="descricao" value="%s" required>
                        </div>
                        <div class="mb-3">
                            <label for="preco" class="form-label">Preço</label>
                            <input type="text" class="form-control" id="preco" name="preco" value="%.2f" required>
                        </div>
                        <div class="mb-3">
                            <label for="estoque" class="form-label">Estoque</label>
                            <input type="number" class="form-control" id="estoque" name="estoque" value="%d" required>
                        </div>
                        <button type="submit" class="btn btn-success">Salvar</button>
                        <a href="/products" class="btn btn-secondary">Cancelar</a>
                    </form>
                </body>
                </html>
                """, title, title, alertHtml, action, nome, descricao, preco, estoque);
    }
}

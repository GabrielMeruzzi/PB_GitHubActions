package org.pb_tp1.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.pb_tp1.model.Product;
import org.pb_tp1.repository.ProductRepository;
import org.pb_tp1.service.ProductService;
import org.pb_tp1.view.ProductView;

import java.util.HashMap;
import java.util.Map;

public class ProductController {
    public ProductController(Javalin app) {
        this(app, new ProductService(new ProductRepository()));
    }

    public ProductController(Javalin app, ProductService service) {
        app.get("/products", ctx -> {
            try {
                String message = ctx.queryParam("message");
                String type = ctx.queryParam("type");
                ctx.html(ProductView.renderList(service.getAllProducts(), message, type));
            } catch (Exception e) {
                ctx.status(500).html(ProductView.renderList(service.getAllProducts(), "Erro ao carregar produtos, tente novamente mais tarde.", "danger"));
            }
        });

        app.get("/products/new", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("message", "");
            ctx.html(ProductView.renderForm(model));
        });

        app.post("/products", ctx -> {
            Map<String, Object> model = new HashMap<>();
            try {
                Product product = extractProductFromForm(ctx, null);
                service.createProduct(product.getNome(), product.getDescricao(), product.getPreco(), product.getEstoque());
                ctx.redirect("/products?message=Produto criado com sucesso!&type=success");
            } catch (Exception e) {
                model.put("message", "Erro ao criar produto, verifique os dados e tente novamente.");
                model.put("messageType", "danger");
            }
            ctx.html(ProductView.renderForm(model));
        });

        app.get("/products/edit/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            Map<String, Object> model = new HashMap<>();
            try {
                Product product = service.getProductById(id);
                model.put("id", product.getId());
                model.put("nome", product.getNome());
                model.put("descricao", product.getDescricao());
                model.put("preco", product.getPreco());
                model.put("estoque", product.getEstoque());
                model.put("message", "");
            } catch (Exception e) {
                model.put("message", "Produto não encontrado.");
                model.put("messageType", "danger");
            }
            ctx.html(ProductView.renderForm(model));
        });

        app.post("/products/edit/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            try {
                Product product = extractProductFromForm(ctx, id);
                service.updateProduct(product.getId(), product.getNome(), product.getDescricao(), product.getPreco(), product.getEstoque());

                ctx.redirect("/products?message=Produto atualizado com sucesso!&type=success");
            } catch (Exception e) {
                ctx.redirect("/products?message=Erro ao atualizar produto.&type=danger");
            }
        });

        app.post("/products/delete/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            try {
                service.deleteProduct(id);
                ctx.redirect("/products?message=Produto deletado com sucesso!&type=success");
            } catch (Exception e) {
                ctx.redirect("/products?message=Erro ao deletar produto.&type=danger");
            }
        });
    }

    private Product extractProductFromForm(Context ctx, Integer id) {
        String nome = ctx.formParam("nome");
        String descricao = ctx.formParam("descricao");
        String precoStr = ctx.formParam("preco").replace(",", ".");
        String estoqueStr = ctx.formParam("estoque");

        double preco = Double.parseDouble(precoStr);
        int estoque = Integer.parseInt(estoqueStr);

        return new Product(id != null ? id : 0, nome, descricao, preco, estoque);
    }
}

package org.pb_tp1.view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductPage {
    private WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://localhost:7000/products");
    }

    public void clickAddNewProduct() {
        driver.findElement(By.xpath("/html/body/a")).click();
    }

    public void fillForm(String nome, String descricao, String preco, String estoque) {
        WebElement nomeInput = driver.findElement(By.id("nome"));
        nomeInput.clear();
        nomeInput.sendKeys(nome);

        WebElement descricaoInput = driver.findElement(By.id("descricao"));
        descricaoInput.clear();
        descricaoInput.sendKeys(descricao);

        WebElement precoInput = driver.findElement(By.id("preco"));
        precoInput.clear();
        precoInput.sendKeys(preco);

        WebElement estoqueInput = driver.findElement(By.id("estoque"));
        estoqueInput.clear();
        estoqueInput.sendKeys(estoque);
    }

    public void submitForm() {
        driver.findElement(By.xpath("/html/body/form/button")).click();
    }

    public void clickEditFirstProduct() {
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[6]/a")).click();
    }

    public void clickDeleteFirstProduct() {
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[6]/form/button")).click();
    }

    public boolean isProductInTable(String nome, String descricao) {
        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        return rows.stream().anyMatch(row -> row.getText().contains(nome)
                && row.getText().contains(descricao));
    }
}

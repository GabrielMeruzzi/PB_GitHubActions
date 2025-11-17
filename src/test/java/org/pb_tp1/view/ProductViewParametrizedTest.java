package org.pb_tp1.view;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductViewParametrizedTest {
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:7000/products");
    }

    @ParameterizedTest
    @CsvSource({
            "Produto A, Descricao A, 10, 5",
            "Produto B, Descricao B, 20, 10",
            "Produto C, Descricao C, 30, 15"
    })
    void createProduct_parametrized(String nome, String descricao, String preco, String estoque) {
        driver.findElement(By.xpath("/html/body/a")).click();

        driver.findElement(By.id("nome")).sendKeys(nome);
        driver.findElement(By.id("descricao")).sendKeys(descricao);
        driver.findElement(By.id("preco")).sendKeys(preco);
        driver.findElement(By.id("estoque")).sendKeys(estoque);

        driver.findElement(By.xpath("/html/body/form/button")).click();

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        boolean found = rows.stream()
                .anyMatch(row -> row.getText().contains(nome)
                        && row.getText().contains(descricao));
        assertTrue(found);
    }

    @ParameterizedTest
    @CsvSource({
            "Produto Editado 1, Nova Desc 1, 50, 25",
            "Produto Editado 2, Nova Desc 2, 60, 30"
    })
    void editProduct_parametrized(String nome, String descricao, String preco, String estoque) {
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[6]/a")).click();

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

        driver.findElement(By.xpath("/html/body/form/button")).click();

        driver.get("http://localhost:7000/products");

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        boolean found = rows.stream()
                .anyMatch(row -> row.getText().contains(nome)
                        && row.getText().contains(descricao));
        assertTrue(found);
    }

    @ParameterizedTest
    @CsvSource({
            "Produto Editado 1, Nova Desc 1",
            "Produto Editado 2, Nova Desc 2"
    })
    void deleteProduct_parametrized(String nome, String descricao) {
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[6]/form/button")).click();

        driver.get("http://localhost:7000/products");

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        boolean found = rows.stream()
                .anyMatch(row -> row.getText().contains(nome)
                        && row.getText().contains(descricao));
        assertFalse(found);
    }
}

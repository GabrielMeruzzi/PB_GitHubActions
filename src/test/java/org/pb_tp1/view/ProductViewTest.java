package org.pb_tp1.view;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductViewTest {
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:7000/products");
    }

    @Test
    @Order(1)
    void createProduct() {
        driver.findElement(By.xpath("/html/body/a")).click();
        driver.findElement(By.id("nome")).sendKeys("Produto de teste");
        driver.findElement(By.id("descricao")).sendKeys("Descricao de testes");
        WebElement precoInput = driver.findElement(By.id("preco"));
        precoInput.clear();
        precoInput.sendKeys("22");
        WebElement estoqueInput = driver.findElement(By.id("estoque"));
        estoqueInput.clear();
        estoqueInput.sendKeys("10");
        driver.findElement(By.xpath("/html/body/form/button")).click();

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        boolean found = rows.stream()
                .anyMatch(row -> row.getText().contains("Produto de teste")
                        && row.getText().contains("Descricao de testes"));
        assertTrue(found);
        driver.quit();
    }

    @Test
    @Order(2)
    void editProduct() {
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[6]/a")).click();

        WebElement nomeInput = driver.findElement(By.id("nome"));
        nomeInput.clear();
        nomeInput.sendKeys("Produto editado");

        WebElement descricaoInput = driver.findElement(By.id("descricao"));
        descricaoInput.clear();
        descricaoInput.sendKeys("Descricao editada");

        WebElement precoInput = driver.findElement(By.id("preco"));
        precoInput.clear();
        precoInput.sendKeys("15");

        WebElement estoqueInput = driver.findElement(By.id("estoque"));
        estoqueInput.clear();
        estoqueInput.sendKeys("5");

        driver.findElement(By.xpath("/html/body/form/button")).click();

        driver.get("http://localhost:7000/products");

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        boolean found = rows.stream()
                .anyMatch(row -> row.getText().contains("Produto editado")
                        && row.getText().contains("Descricao editada"));
        assertTrue(found);
        driver.quit();
    }

    @Order(3)
    @Test
    void deleteProduct() {
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[6]/form/button")).click();

        driver.get("http://localhost:7000/products");

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        boolean found = rows.stream()
                .anyMatch(row -> row.getText().contains("Produto editado")
                        && row.getText().contains("Descricao editada"));
        assertFalse(found);
        driver.quit();
    }

    @Order(4)
    @Test
    void createProductWithInvalidEstoqueNumber() {
        driver.findElement(By.xpath("/html/body/a")).click();
        driver.findElement(By.id("nome")).sendKeys("Produto de teste");
        driver.findElement(By.id("descricao")).sendKeys("Descricao de testes");
        WebElement precoInput = driver.findElement(By.id("preco"));
        precoInput.clear();
        precoInput.sendKeys("22");
        WebElement estoqueInput = driver.findElement(By.id("estoque"));
        estoqueInput.clear();
        estoqueInput.sendKeys("-10");
        driver.findElement(By.xpath("/html/body/form/button")).click();

        String errorMsg = driver.findElement(By.className("alert")).getText();
        assertEquals("Erro ao criar produto, verifique os dados e tente novamente.", errorMsg);

        driver.quit();
    }
}

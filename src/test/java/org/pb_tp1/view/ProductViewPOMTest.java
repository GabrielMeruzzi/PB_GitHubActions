package org.pb_tp1.view;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductViewPOMTest {
    private WebDriver driver;
    private ProductPage productPage;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        productPage = new ProductPage(driver);
        productPage.open();
    }

    @Test
    @Order(1)
    void createProduct() throws InterruptedException {
        productPage.clickAddNewProduct();
        productPage.fillForm("Produto de teste", "Descricao de testes", "22", "10");
        productPage.submitForm();

        Thread.sleep(3000);
        assertTrue(productPage.isProductInTable("Produto de teste", "Descricao de testes"));
        driver.quit();
    }

    @Test
    @Order(2)
    void editProduct() throws InterruptedException {
        productPage.clickEditFirstProduct();
        productPage.fillForm("Produto editado", "Descricao editada", "15", "5");
        productPage.submitForm();

        productPage.open();
        Thread.sleep(3000);
        assertTrue(productPage.isProductInTable("Produto editado", "Descricao editada"));
        driver.quit();
    }

    @Test
    @Order(3)
    void deleteProduct() throws InterruptedException {
        productPage.clickDeleteFirstProduct();
        productPage.open();

        Thread.sleep(3000);
        assertFalse(productPage.isProductInTable("Produto editado", "Descricao editada"));
        driver.quit();
    }
}

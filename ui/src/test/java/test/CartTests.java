package test;

import base.FlexibleUiBaseTest;
import org.openqa.selenium.By;
import pages.LoginPage;
import pages.ProductPage;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.*;
import org.junit.After;
import static org.junit.Assert.*;

@Feature("UI Cart Tests")
public class CartTests extends FlexibleUiBaseTest {

    private ProductPage productPage;

    @Override
    @Before
    public void setUp() {
        // Переопределяем метод setUp родительского класса
        try {
            // Сначала вызываем родительский setUp
            super.setUp();
        } catch (Exception e) {
            System.out.println("Родительский setUp завершился, продолжаем...");
        }

        // Очищаем cookies
        if (driver != null) {
            driver.manage().deleteAllCookies();
        }

        // Выполняем логин
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        productPage = new ProductPage(driver);
    }

    @Test
    public void testAddSingleItemToCart() {
        productPage.addFirstProductToCart();
        assertEquals(1, productPage.getCartItemCount());
    }

    @Test
    public void testAddMultipleItemsToCart() {
        productPage.addProductToCartByIndex(0);
        productPage.addProductToCartByIndex(1);
        assertEquals(2, productPage.getCartItemCount());
    }

    @Test
    public void testCartCountAfterLogin() {
        assertEquals(0, driver.findElements(By.className("shopping_cart_badge")).size());
    }

    @Override
    @After
    public void tearDown() {
        super.tearDown();
    }
}
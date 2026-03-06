package test;

import base.UiBaseTest;
import pages.LoginPage;
import pages.ProductPage;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTests extends UiBaseTest {

    @Test
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        ProductPage productPage = new ProductPage(driver);
        assertEquals("Products", productPage.getPageTitle());
    }

    @Test
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrong_user", "wrong_pass");

        assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"));
    }

    @Test
    public void testEmptyFields() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLogin();

        assertTrue(loginPage.getErrorMessage().contains("Username is required"));
    }
}
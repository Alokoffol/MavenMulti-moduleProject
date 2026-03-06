package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {

    private WebDriver driver;

    private By productTitle = By.className("title");
    private By inventoryItem = By.className("inventory_item");
    private By addToCartButton = By.cssSelector(".btn_inventory");
    private By cartBadge = By.className("shopping_cart_badge");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle() {
        return driver.findElement(productTitle).getText();
    }

    public int getProductCount() {
        return driver.findElements(inventoryItem).size();
    }

    public void addFirstProductToCart() {
        driver.findElements(addToCartButton).get(0).click();
    }

    public void addProductToCartByIndex(int index) {
        driver.findElements(addToCartButton).get(index).click();
    }

    public int getCartItemCount() {
        String count = driver.findElement(cartBadge).getText();
        return Integer.parseInt(count);
    }
}
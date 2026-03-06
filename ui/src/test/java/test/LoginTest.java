package test;

import io.qameta.allure.*;
import base.UiBaseTest;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;


@Feature("UI Login Tests")
public class LoginTest extends UiBaseTest {

    @Test
    public void testLogin() {
        System.setProperty("logback.configurationFile", "logback-json.xml");
        logStep("Запуск браузера");
        WebDriver driver = new ChromeDriver();

        try {
            logStep("Открытие страницы логина");
            driver.get("https://the-internet.herokuapp.com/login");
            log.debug("Текущий URL: {}", driver.getCurrentUrl());

            logStep("Ввод логина");
            driver.findElement(By.id("username")).sendKeys("tomsmith");

            logStep("Ввод пароля");
            driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");

            logStep("Нажатие кнопки входа");
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            logStep("Проверка результата");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            String message = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
            ).getText();

            log.info("Сообщение: {}", message.trim());

            if (message.contains("You logged into")) {
                log.info("✅ Тест пройден успешно");
            } else {
                log.error("❌ Тест не пройден. Ожидалось сообщение об успехе");
            }

        } catch (Exception e) {
            logError("Ошибка в тесте", e);
            throw e;
        } finally {
            logStep("Закрытие браузера");
            driver.quit();
        }
    }
}
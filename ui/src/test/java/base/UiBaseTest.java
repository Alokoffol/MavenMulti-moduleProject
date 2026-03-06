package base;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class UiBaseTest {

    protected WebDriver driver;
    protected final String BASE_URL = "https://www.saucedemo.com";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Before
    public void setUp() {
        log.info("=".repeat(60));
        log.info("НАЧАЛО UI ТЕСТА: {}", getClass().getSimpleName());
        log.info("=".repeat(60));

        logStep("Настройка WebDriver");
        WebDriverManager.chromedriver().setup();

        // Теперь IDEA найдет этот класс!
        ChromeOptions options = ChromeOptionsConfig.createChromeOptions(false);

        driver = new ChromeDriver(options);

        logStep("Настройка окна браузера");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        logStep("Открытие страницы: {}", BASE_URL);
        driver.get(BASE_URL);

        log.info("✅ Браузер запущен, страница загружена");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            logStep("Закрытие браузера");
            driver.quit();
            log.info("✅ Браузер закрыт");
        }

        log.info("=".repeat(60));
        log.info("ЗАВЕРШЕНИЕ UI ТЕСТА: {}", getClass().getSimpleName());
        log.info("=".repeat(60));
    }

    protected void logStep(String step, Object... args) {
        if (args.length > 0) {
            log.info("🔹 ШАГ: " + step, args);
        } else {
            log.info("🔹 ШАГ: {}", step);
        }
    }

    protected void logDebug(String message) {
        log.debug("🔸 DEBUG: {}", message);
    }

    protected void logError(String message, Throwable t) {
        log.error("❌ ОШИБКА: {}", message, t);
    }
}
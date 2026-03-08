package base;

import com.example.utils.DockerWebDriverFactory;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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

        // Читаем настройки
        boolean headless = Boolean.parseBoolean(System.getenv("HEADLESS"));
        boolean useDocker = Boolean.parseBoolean(System.getenv("USE_DOCKER"));
        String browser = System.getenv("BROWSER") != null ? System.getenv("BROWSER") : "chrome";

        log.info("🔹 Браузер: {}", browser);
        log.info("🔹 Headless mode: {}", headless);
        log.info("🔹 Docker mode: {}", useDocker);

        if (useDocker) {
            driver = createDockerDriver(browser);
        } else {
            driver = createLocalDriver(browser, headless);
        }

        logStep("Настройка окна браузера");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        logStep("Открытие страницы: {}", BASE_URL);
        driver.get(BASE_URL);

        log.info("✅ Браузер запущен, страница загружена");
    }

    private WebDriver createLocalDriver(String browser, boolean headless) {
        logStep("Настройка локального WebDriver");
        WebDriverManager.chromedriver().setup();

        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                return new FirefoxDriver(firefoxOptions);

            case "chrome":
            default:
                ChromeOptions options = ChromeOptionsConfig.createChromeOptions(headless);
                return new ChromeDriver(options);
        }
    }

    private WebDriver createDockerDriver(String browser) {
        log.info("🐳 Подключаемся к Selenium в Docker");

        switch (browser.toLowerCase()) {
            case "firefox":
                return DockerWebDriverFactory.createFirefoxInDocker();
            case "chrome":
            default:
                return DockerWebDriverFactory.createChromeInDocker();
        }
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
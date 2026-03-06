package base;

import com.example.utils.ConfigReader;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;


public class FlexibleUiBaseTest {

    protected WebDriver driver;
    protected String baseUrl;

    // Флаг для отключения проверки утечек паролей
    protected boolean disablePasswordLeakDetection = true;

    // Можно менять прямо в коде перед запуском
    protected String environment = "ui-dev";  // ← МЕНЯЙ ЗДЕСЬ!

    @Before
    public void setUp() {
        // Устанавливаем окружение перед загрузкой
        ConfigReader.setEnvironment(environment);

        baseUrl = ConfigReader.get("base.url");
        String browser = ConfigReader.get("browser", "chrome");
        boolean headless = ConfigReader.getBoolean("headless");
        int timeout = ConfigReader.getInt("timeout.seconds", 10);

        System.out.println("🔹 Окружение: " + environment);
        System.out.println("🔹 Браузер: " + browser);
        System.out.println("🔹 Headless: " + headless);
        System.out.println("🔹 Таймаут: " + timeout + " сек");
        System.out.println("🔹 Base URL: " + baseUrl);
        System.out.println("🔹 Защита от утечек паролей: " + (disablePasswordLeakDetection ? "ОТКЛЮЧЕНА" : "ВКЛЮЧЕНА"));

        WebDriverManager.chromedriver().setup();
        driver = createDriver(browser, headless);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.get(baseUrl);
    }

    private WebDriver createDriver(String browser, boolean headless) {
        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                return new FirefoxDriver(firefoxOptions);

            case "chrome":
            default:
                ChromeOptions chromeOptions = base.ChromeOptionsConfig.createChromeOptions(headless);
                return new ChromeDriver(chromeOptions);
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
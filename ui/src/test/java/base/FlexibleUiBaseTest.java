package base;

import com.example.utils.ConfigReader;
import com.example.utils.DockerWebDriverFactory;
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

    protected boolean disablePasswordLeakDetection = true;
    protected String environment = "ui-dev";

    @Before
    public void setUp() {
        ConfigReader.setEnvironment(environment);

        baseUrl = ConfigReader.get("base.url");
        String browser = ConfigReader.get("browser", "chrome");
        boolean headless = ConfigReader.getBoolean("headless");
        boolean useDocker = ConfigReader.getBoolean("docker.enabled");
        int timeout = ConfigReader.getInt("timeout.seconds", 10);

        System.out.println("🔹 Окружение: " + environment);
        System.out.println("🔹 Браузер: " + browser);
        System.out.println("🔹 Headless: " + headless);
        System.out.println("🔹 Docker: " + useDocker);
        System.out.println("🔹 Таймаут: " + timeout + " сек");
        System.out.println("🔹 Base URL: " + baseUrl);
        System.out.println("🔹 Защита от утечек паролей: " + (disablePasswordLeakDetection ? "ОТКЛЮЧЕНА" : "ВКЛЮЧЕНА"));

        if (useDocker) {
            driver = createDockerDriver(browser);
        } else {
            driver = createLocalDriver(browser, headless);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.get(baseUrl);
    }

    private WebDriver createLocalDriver(String browser, boolean headless) {
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
                ChromeOptions chromeOptions = ChromeOptionsConfig.createChromeOptions(headless);
                return new ChromeDriver(chromeOptions);
        }
    }

    private WebDriver createDockerDriver(String browser) {
        System.out.println("🐳 Подключаемся к Selenium в Docker");

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
            driver.quit();
        }
    }
}
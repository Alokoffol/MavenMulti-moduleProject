import com.example.utils.ConfigReader;
import org.junit.Test;
import static org.junit.Assert.*;



public class ConfigTest {

    @Test
    public void testConfigValues() {
        System.out.println("=== ТЕСТ КОНФИГУРАЦИИ ===");

        String env = System.getProperty("env", "dev");
        ConfigReader.setEnvironment(env);

        String baseUrl = ConfigReader.get("base.url");
        String browser = ConfigReader.get("browser");
        int timeout = ConfigReader.getInt("timeout.seconds");
        String logLevel = ConfigReader.get("log.level", "INFO");

        System.out.println("Окружение: " + env);
        System.out.println("Base URL: " + baseUrl);
        System.out.println("Браузер: " + browser);
        System.out.println("Таймаут: " + timeout);
        System.out.println("Log level: " + logLevel);

        assertNotNull(baseUrl);
        assertNotNull(browser);
        assertTrue(timeout > 0);
    }
}
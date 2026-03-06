package base;

import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

public class ChromeOptionsConfig {

    public static ChromeOptions createChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        // Отключаем проверку утечек паролей
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection.enabled", false);
        prefs.put("safebrowsing.enabled", false);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        // Добавляем необходимые аргументы
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-password-manager-reauthentication");
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--incognito");

        if (headless) {
            options.addArguments("--headless");
        }

        return options;
    }
}
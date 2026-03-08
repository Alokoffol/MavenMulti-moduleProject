package com.example.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class DockerWebDriverFactory {

    private static final String SELENIUM_HUB_URL = "http://localhost:4444/wd/hub";

    public static WebDriver createChromeInDocker() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("chrome");
            capabilities.setVersion("latest");
            capabilities.setCapability("platform", "LINUX");

            return new RemoteWebDriver(new URL(SELENIUM_HUB_URL), capabilities);

        } catch (Exception e) {
            throw new RuntimeException("Не удалось подключиться к Selenium в Docker", e);
        }
    }

    public static WebDriver createFirefoxInDocker() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("firefox");

            return new RemoteWebDriver(new URL(SELENIUM_HUB_URL), capabilities);

        } catch (Exception e) {
            throw new RuntimeException("Не удалось подключиться к Selenium в Docker", e);
        }
    }
}
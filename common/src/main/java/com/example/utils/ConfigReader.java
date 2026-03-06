package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();
    private static String environment = System.getProperty("env", "dev"); // dev по умолчанию

    static {
        loadProperties();
    }

    private static void loadProperties() {
        String fileName = "config-" + environment + ".properties";
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.out.println("Файл " + fileName + " не найден, загружаем config-dev.properties");
                try (InputStream defaultInput = ConfigReader.class.getClassLoader()
                        .getResourceAsStream("config-dev.properties")) {
                    properties.load(defaultInput);
                }
            } else {
                properties.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public static void setEnvironment(String env) {
        environment = env;
        loadProperties(); // перезагружаем с новым окружением
    }
}
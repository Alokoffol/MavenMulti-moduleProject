package com.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoggerUtils {

    private static final Logger log = LoggerFactory.getLogger(LoggerUtils.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void logRequest(String method, String url, Object body) {
        log.info("🌐 ЗАПРОС: {} {}", method, url);
        if (body != null) {
            try {
                log.debug("Тело запроса: {}", mapper.writeValueAsString(body));
            } catch (Exception e) {
                log.debug("Тело запроса: {}", body);
            }
        }
    }

    public static void logResponse(int status, Object body) {
        log.info("📦 ОТВЕТ: статус {}", status);
        if (body != null) {
            try {
                log.debug("Тело ответа: {}", mapper.writeValueAsString(body));
            } catch (Exception e) {
                log.debug("Тело ответа: {}", body);
            }
        }
    }

    public static void logTestStart(String testName) {
        log.info("🚀 ЗАПУСК ТЕСТА: {}", testName);
        log.info("=".repeat(50));
    }

    public static void logTestEnd(String testName, boolean success) {
        log.info("=".repeat(50));
        if (success) {
            log.info("✅ ТЕСТ ПРОЙДЕН: {}", testName);
        } else {
            log.info("❌ ТЕСТ УПАЛ: {}", testName);
        }
    }

    public static void logStep(String step) {
        log.info("🔹 ШАГ: {}", step);
    }

    public static void logError(String message, Throwable t) {
        log.error("❌ ОШИБКА: {}", message, t);
    }

    public static void logWithFields(String message, Map<String, Object> fields) {
        StringBuilder sb = new StringBuilder(message);
        sb.append(" {");
        fields.forEach((k, v) -> sb.append(k).append("=").append(v).append(", "));
        sb.append("}");
        log.info(sb.toString());
    }
}
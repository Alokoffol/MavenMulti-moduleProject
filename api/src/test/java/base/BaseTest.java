package base;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BaseTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.filters(
                new AllureRestAssured(),
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );
    }

    // ===== МЕТОДЫ ДЛЯ ALLURE =====

    @Step("{0}")
    public void logStep(String message) {
        System.out.println("🔷 " + message);
        log.info("ШАГ: {}", message);  // Дублируем в SLF4J
    }

    @Attachment(value = "Запрос", type = "application/json")
    public String attachRequest(Object request) {
        log.debug("Прикрепляем запрос: {}", request);
        return request.toString();
    }

    @Attachment(value = "Ответ сервера", type = "application/json")
    public String attachResponse(String response) {
        log.debug("Прикрепляем ответ длиной: {} символов", response.length());
        return response;
    }

    @Step("Проверка статус кода")
    public void verifyStatusCode(int actual, int expected) {
        log.info("Проверка статуса: ожидаем {}, получили {}", expected, actual);
        assertEquals("Статус код не совпадает", expected, actual);
    }

    @Step("Проверка что объект не null")
    public void verifyNotNull(Object obj, String fieldName) {
        log.debug("Проверка что {} не null", fieldName);
        assertNotNull(fieldName + " не должен быть null", obj);
    }

    @Step("Проверка статус кода (допустимо несколько вариантов)")
    public void verifyStatusCode(int actual, int... expected) {
        for (int exp : expected) {
            if (actual == exp) {
                log.info("✅ Статус код {} соответствует ожидаемому", actual);
                return;
            }
        }
        String msg = "❌ Статус код " + actual + " не соответствует ожидаемым: " + Arrays.toString(expected);
        log.error(msg);
        fail(msg);
    }

    // ===== НОВЫЕ МЕТОДЫ ДЛЯ ЛОГИРОВАНИЯ =====

    protected void logDebug(String message) {
        log.debug(message);
    }

    protected void logError(String message, Throwable t) {
        log.error(message, t);
    }
}
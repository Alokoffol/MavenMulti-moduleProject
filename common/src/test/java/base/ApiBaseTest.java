package base;

import com.example.utils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

public class ApiBaseTest {

    protected static RequestSpecification requestSpec;

    @BeforeClass
    public static void setUp() {
        String baseUrl = ConfigReader.get("base.url");

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        System.out.println("🔹 Запуск тестов на окружении: " +
                System.getProperty("env", "dev"));
        System.out.println("🔹 Base URL: " + baseUrl);
        System.out.println("🔹 Timeout: " + ConfigReader.getInt("timeout.seconds") + " сек");
    }
}
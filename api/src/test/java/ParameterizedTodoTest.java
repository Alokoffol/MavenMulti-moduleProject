import base.BaseTest;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import com.example.models.Todo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import io.qameta.allure.*;

import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
@Feature("Параметризованные тесты")
public class ParameterizedTodoTest extends BaseTest {

    private int userId;
    private String title;
    private boolean completed;
    private String description;

    // Конструктор с параметрами
    public ParameterizedTodoTest(int userId, String title, boolean completed, String description) {
        this.userId = userId;
        this.title = title;
        this.completed = completed;
        this.description = description;
    }

    // Данные для тестов
    @Parameters(name = "Тест {index}: {3}")  // name - как будет называться тест в отчёте
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, "Купить хлеб", false, "Обычная задача"},
                {2, "Сделать домашку", true, "Завершённая задача"},
                {1, "Очень длинный заголовок, который может быть длиннее обычного и проверить как API с этим справляется", false, "Длинный заголовок"},
                {3, "", false, "Пустой заголовок"},
                {5, "Спецсимволы !@#$%", true, "Спецсимволы в заголовке"}
        });
    }

    @Test
    @Story("Создание Todo с разными данными")
    @Severity(SeverityLevel.NORMAL)
    @Description("Параметризованный тест создания Todo")
    public void testCreateTodoWithDifferentData() {
        logStep("Тест: " + description);
        logStep("Создаём Todo с userId=" + userId + ", title='" + title + "', completed=" + completed);

        Todo newTodo = new Todo();
        newTodo.setUserId(userId);
        newTodo.setTitle(title);
        newTodo.setCompleted(completed);

        attachRequest(newTodo);

        logStep("Отправляем POST запрос");
        Todo createdTodo = given()
                .contentType(ContentType.JSON)
                .body(newTodo)
                .when()
                .post("/todos")
                .then()
                .statusCode(201)
                .extract()
                .as(Todo.class);

        attachResponse(createdTodo.toString());

        logStep("Проверяем результат");
        verifyNotNull(createdTodo.getId(), "ID");
        assertEquals("userId должен совпадать", userId, createdTodo.getUserId());
        assertEquals("title должен совпадать", title, createdTodo.getTitle());
        assertEquals("completed должен совпадать", completed, createdTodo.isCompleted());

        logStep("✅ Тест для '" + description + "' пройден");
    }
}
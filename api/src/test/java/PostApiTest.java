import base.BaseTest;
import com.example.models.Post;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class PostApiTest extends BaseTest {

    @Test
    public void testGetPosts() {
        logStep("Отправляем GET запрос на /posts");

        Response response = given()
                .baseUri(BASE_URL)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .response();

        logDebug("Статус код: " + response.statusCode());
        logDebug("Content-Type: " + response.contentType());

        Post[] posts = response.as(Post[].class);
        log.info("Получено постов: {}", posts.length);

        assertTrue("Должен быть хотя бы 1 пост", posts.length > 0);
        log.info("Первый пост: {}", posts[0].getTitle());
    }

    @Test
    public void testCreatePost() {
        logStep("Создаем новый пост");

        Post newPost = new Post();
        newPost.setUserId(1);
        newPost.setTitle("Тестовый пост " + System.currentTimeMillis());
        newPost.setBody("Содержание тестового поста");

        log.debug("Отправляемые данные: {}", newPost);

        Post createdPost = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(newPost)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .extract()
                .as(Post.class);

        log.info("Создан пост с ID: {}", createdPost.getId());
        log.debug("Полный ответ: {}", createdPost);

        assertNotNull("ID не должен быть null", createdPost.getId());
        assertEquals("Заголовок должен совпадать", newPost.getTitle(), createdPost.getTitle());
    }
}
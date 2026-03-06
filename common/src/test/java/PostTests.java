
import base.ApiBaseTest;
import com.example.models.Post;
import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostTests extends ApiBaseTest {

    @Test
    public void testGetPosts() {
        given()
                .spec(requestSpec)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testCreatePost() {
        Post newPost = new Post(1, 0, "Тестовый пост", "Содержание");

        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(newPost)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("id", notNullValue());
    }
}
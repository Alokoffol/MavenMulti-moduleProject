import base.BaseTest;
import io.qameta.allure.Feature;
import com.example.models.Post;
import org.junit.Test;
import io.qameta.allure.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


@Feature("Get запросы")
public class GetPostTests extends BaseTest {

    @Test
    public void getAllPostsTest() {
        List<Post> posts = given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("$", not(empty()))
                .extract()
                .jsonPath()
                .getList("", Post.class);

        assertThat(posts, is(not(empty())));
        System.out.println("Количество постов: " + posts.size());
    }

    @Test
    public void getPostByIDTest() {
        Post post = given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .extract()
                .as(Post.class);

        assertEquals("Пост должен иметь id = 1", 1, post.getId());
        System.out.println("Пост с id = 1 получен");
    }

    @Test
    public void getUserPostTest() {

        List<Post> posts = given()
                .queryParam("userId", 1)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("$", not(empty()))
                .extract()
                .jsonPath()
                .getList("", Post.class);

        for (Post post : posts) {
            assertEquals("userId должен быть 1", 1, post.getUserId());
            assertNotNull("Title не должен быть null", post.getTitle());
            assertFalse("Title не должен быть пустым", post.getTitle().isEmpty());
        }

        System.out.println("Найдено постов для userId=1: " + posts.size());
    }

}

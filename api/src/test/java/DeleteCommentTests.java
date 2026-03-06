import base.BaseTest;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import com.example.models.Comment;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import static com.example.utils.TestDataCreator.createComment;

@Feature("Delete запросы")
public class DeleteCommentTests extends BaseTest {

    // Удаление существующего комментария
    @Test
    public void deleteExistingCommentTest() {
        Comment comment = createComment();
        int commentId = comment.getId();

        Response deleteResponse = given()
                .pathParam("commentId", commentId)
                .when()
                .delete("/comments/{commentId}")
                .then()
                .extract()
                .response();

        int status = deleteResponse.getStatusCode();
        assertTrue(status == 200 || status == 204);

        given()
                .pathParam("commentId", commentId)
                .when()
                .get("/comments/{commentId}")
                .then()
                .statusCode(404);

        System.out.println("✅ Комментарий " + commentId + " удалён");
    }
}
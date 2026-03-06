package com.example.utils;

import com.example.models.Comment;
import com.example.models.Post;
import com.example.models.Todo;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

public class TestDataCreator {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    // === TODO METHODS ===
    public static Todo createTodo() {
        Todo todo = new Todo();
        todo.setUserId(1);
        todo.setTitle("Задача для тестов");
        todo.setCompleted(false);

        return given()
                .contentType(ContentType.JSON)
                .body(todo)
                .when()
                .post(BASE_URL + "/todos")
                .then()
                .statusCode(201)
                .extract()
                .as(Todo.class);
    }

    public static Todo createTodoWithParams(int userId, String title, boolean completed) {
        Todo todo = new Todo();
        todo.setUserId(userId);
        todo.setTitle(title);
        todo.setCompleted(completed);

        return given()
                .contentType(ContentType.JSON)
                .body(todo)
                .when()
                .post(BASE_URL + "/todos")
                .then()
                .statusCode(201)
                .extract()
                .as(Todo.class);
    }

    // === POST METHODS ===
    public static Post createPost() {
        Post post = new Post();
        post.setUserId(1);
        post.setTitle("Пост для тестов");
        post.setBody("Содержимое поста для тестов");

        return given()
                .contentType(ContentType.JSON)
                .body(post)
                .when()
                .post(BASE_URL + "/posts")
                .then()
                .statusCode(201)
                .extract()
                .as(Post.class);
    }

    public static Post createPostWithParams(int userId, String title, String body) {
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(title);
        post.setBody(body);

        return given()
                .contentType(ContentType.JSON)
                .body(post)
                .when()
                .post(BASE_URL + "/posts")
                .then()
                .statusCode(201)
                .extract()
                .as(Post.class);
    }

    // === COMMENT METHODS ===
    public static Comment createComment() {
        Comment comment = new Comment();
        comment.setPostId(1);
        comment.setName("Тестовый комментатор");
        comment.setEmail("test@mail.com");
        comment.setBody("Комментарий для тестов");

        return given()
                .contentType(ContentType.JSON)
                .body(comment)
                .when()
                .post(BASE_URL + "/comments")
                .then()
                .statusCode(201)
                .extract()
                .as(Comment.class);
    }

    public static Comment createCommentWithParams(int postId, String name, String email, String body) {
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setName(name);
        comment.setEmail(email);
        comment.setBody(body);

        return given()
                .contentType(ContentType.JSON)
                .body(comment)
                .when()
                .post(BASE_URL + "/comments")
                .then()
                .statusCode(201)
                .extract()
                .as(Comment.class);
    }
}
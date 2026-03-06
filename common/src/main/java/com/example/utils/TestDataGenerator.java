package com.example.utils;

import com.example.models.Comment;
import com.example.models.Post;
import com.example.models.Todo;

import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {

    private static final String[] TITLES = {
            "Купить продукты", "Сделать домашку", "Позвонить маме",
            "Сходить в спортзал", "Почитать книгу", "Написать код"
    };

    private static final String[] BODIES = {
            "Нужно купить хлеб, молоко и яйца",
            "Закончить задачи по автоматизации",
            "Узнать как дела и поздравить с праздником",
            "Пробежка и упражнения на пресс",
            "Прочитать главу про API тестирование",
            "Реализовать POST тесты для проекта"
    };

    private static final String[] NAMES = {
            "Иван Петров", "Мария Сидорова", "Алексей Иванов",
            "Елена Козлова", "Дмитрий Смирнов", "Ольга Новикова"
    };

    private static final String[] EMAILS = {
            "ivan@mail.com", "maria@mail.com", "alex@mail.com",
            "elena@mail.com", "dmitry@mail.com", "olga@mail.com"
    };

    public static Todo generateTodo() {
        Todo todo = new Todo();
        todo.setUserId(ThreadLocalRandom.current().nextInt(1, 6));
        todo.setTitle(TITLES[ThreadLocalRandom.current().nextInt(TITLES.length)]);
        todo.setCompleted(ThreadLocalRandom.current().nextBoolean());
        // id не ставим - сервер сам создаст
        return todo;
    }

    public static Post generatePost() {
        Post post = new Post();
        post.setUserId(ThreadLocalRandom.current().nextInt(1, 6));
        post.setTitle(TITLES[ThreadLocalRandom.current().nextInt(TITLES.length)]);
        post.setBody(BODIES[ThreadLocalRandom.current().nextInt(BODIES.length)]);
        return post;
    }

    public static Comment generateComment() {
        Comment comment = new Comment();
        comment.setPostId(ThreadLocalRandom.current().nextInt(1, 6));
        comment.setName(NAMES[ThreadLocalRandom.current().nextInt(NAMES.length)]);
        comment.setEmail(EMAILS[ThreadLocalRandom.current().nextInt(EMAILS.length)]);
        comment.setBody(BODIES[ThreadLocalRandom.current().nextInt(BODIES.length)]);
        return comment;
    }
}
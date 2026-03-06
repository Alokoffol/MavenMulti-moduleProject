// src/main/java/com/example/repository/UserRepository.java
package com.example.repository;

import com.example.models.User;

public interface UserRepository {
    User findByEmail(String email);
    void save(User user);
    void delete(String email);
}
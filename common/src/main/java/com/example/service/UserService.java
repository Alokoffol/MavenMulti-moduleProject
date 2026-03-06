package com.example.service;

import com.example.repository.UserRepository;
import com.example.models.User;

public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean registerUser(String email) {
        if (repository.findByEmail(email) != null) {
            return false; // уже есть
        }
        User user = new User(email);
        repository.save(user);
        return true;
    }

    public boolean deleteUser(String email) {
        User user = repository.findByEmail(email);
        if (user == null) {
            return false;
        }
        repository.delete(email);
        return true;
    }
}
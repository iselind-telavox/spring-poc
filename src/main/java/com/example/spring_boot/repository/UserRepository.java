package com.example.spring_boot.repository;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
}

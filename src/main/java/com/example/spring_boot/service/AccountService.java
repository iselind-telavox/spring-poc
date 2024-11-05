package com.example.spring_boot.service;

import java.util.Optional;

public interface AccountService {
    Optional<User> getUser(String username);
}

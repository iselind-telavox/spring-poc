package com.example.spring_boot.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public UserRepositoryImpl() {
        List<User> tmpUsers = new ArrayList<>();
        tmpUsers.add(new User() {{
            setFirstName("John");
            setLastName("Smith");
            setUsername("johnsmith");
        }});
        tmpUsers.add(new User() {{
            setFirstName("Patrik");
            setLastName("Pappsson");
            setUsername("user2");
        }});

        for (User user : tmpUsers) {
            users.put(user.getUsername(), user);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User u = users.getOrDefault(username, null);
        return Optional.ofNullable(u);
    }
}

package com.example.spring_boot.service;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class User {
    private String firstName;
    private String lastName;
    private String username;

    public static Optional<User> from(Optional<com.example.spring_boot.repository.User> u) {
        User user = null;
        if (u.isPresent()) {
            com.example.spring_boot.repository.User repoUser = u.get();
            user = new User();
            user.setFirstName(repoUser.getFirstName());
            user.setLastName(repoUser.getLastName());
            user.setUsername(repoUser.getUsername());
        }
        return Optional.ofNullable(user);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

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

    public static Optional<User> from(com.example.spring_boot.repository.User u) {
        User user = null;
        if (u != null) {
            user = new User();
            user.setFirstName(u.getFirstName());
            user.setLastName(u.getLastName());
            user.setUsername(u.getUsername());
        }
        return Optional.ofNullable(user);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

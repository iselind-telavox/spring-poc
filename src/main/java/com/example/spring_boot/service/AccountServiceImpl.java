package com.example.spring_boot.service;

import com.example.spring_boot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;

    public AccountServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUser(String username) {
        Optional<com.example.spring_boot.repository.User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return User.from(user.get());
        }
        return Optional.empty();
    }
}

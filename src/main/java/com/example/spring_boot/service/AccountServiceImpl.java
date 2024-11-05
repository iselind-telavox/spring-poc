package com.example.spring_boot.service;

import com.example.spring_boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RiskAssessor riskAssessor;

    @Override
    public Optional<User> getUser(String username) {
        Optional<com.example.spring_boot.repository.User> user = userRepository.findByUsername(username);
        return User.from(user);
    }
}

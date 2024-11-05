package com.example.spring_boot.service;

import com.example.spring_boot.repository.User;
import com.example.spring_boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RiskAssessorImpl implements RiskAssessor {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean assess(String name) {
        Optional<User> user = userRepository.findByUsername(name);
        if (user.isEmpty()) {
            return false;
        }

        User u = user.get();
        String fullName = u.getFirstName() + " " + u.getLastName();
        return fullName.contains("p");
    }
}

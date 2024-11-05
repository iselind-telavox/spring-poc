package com.example.spring_boot.service;

import com.example.spring_boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiskAssessorImpl implements RiskAssessor {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isSecurityRisk(String name) {
        return name.contains("p");
    }

    @Override
    public boolean isSecurityRisk(com.example.spring_boot.service.User user) {
        return isSecurityRisk(user.toString());
    }
}

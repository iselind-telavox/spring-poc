package com.example.spring_boot.service;

import org.springframework.stereotype.Service;

@Service
public class RiskAssessorImpl implements RiskAssessor {
    private boolean isSecurityRisk(String name) {
        return name.contains("p");
    }

    @Override
    public Boolean isSecurityRisk(User user) {
        return isSecurityRisk(user.toString());
    }
}

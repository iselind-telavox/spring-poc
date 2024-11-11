package com.example.spring_boot.service;

import org.springframework.stereotype.Service;

@Service
public class RiskAssessorImpl implements RiskAssessor {
    private boolean isSecurityRisk(String name) {
        return name.contains("p");
    }

    @Override
    public boolean isSecurityRisk(com.example.spring_boot.service.User user) {
        return isSecurityRisk(user.toString());
    }
}

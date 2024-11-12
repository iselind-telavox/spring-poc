package com.example.spring_boot.service;

public interface RiskAssessor {
    /**
     * Assess name for being a security risk
     */
    Boolean isSecurityRisk(User user);
}